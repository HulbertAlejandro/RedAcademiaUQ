package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.BuscarContenidoDTO;
import co.edu.uniquindio.red_academica.dto.CrearContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.ContenidoAcademico;
import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.repositorios.ContenidoAcademicoRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.ContenidoAcademicoService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContenidoAcademicoServiceImpl implements ContenidoAcademicoService {

    private final ContenidoAcademicoRepository contenidoRepository;
    private final GridFsTemplate gridFsTemplate;

    @Override
    public String subirContenido(CrearContenidoAcademicoDTO dto, MultipartFile archivo) throws Exception {

        if (archivo == null || archivo.isEmpty()) {
            throw new Exception("Debes seleccionar un archivo");
        }

        validarArchivo(dto.tipoContenido(), archivo);

        ObjectId archivoId = gridFsTemplate.store(
                archivo.getInputStream(),
                archivo.getOriginalFilename(),
                archivo.getContentType()
        );

        ContenidoAcademico contenido = ContenidoAcademico.builder()
                .titulo(dto.titulo())
                .tema(dto.tema())
                .autor(dto.autor())
                .tipoContenido(dto.tipoContenido())
                .nombreArchivo(archivo.getOriginalFilename())
                .contentType(archivo.getContentType())
                .tamanoBytes(archivo.getSize())
                .archivoId(archivoId.toHexString())
                .fechaCreacion(LocalDateTime.now())
                .build();

        contenidoRepository.save(contenido);

        return contenido.getId();
    }

    @Override
    public ContenidoAcademico obtenerContenidoPorId(String id) throws Exception {
        return contenidoRepository.findById(id)
                .orElseThrow(() -> new Exception("Contenido académico no encontrado"));
    }

    @Override
    public List<ContenidoAcademico> obtenerTodosContenidos() {
        return contenidoRepository.findAll();
    }

    @Override
    public byte[] obtenerArchivoContenido(String id) throws Exception {
        ContenidoAcademico contenido = obtenerContenidoPorId(id);

        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(contenido.getArchivoId())))
        );

        if (file == null) {
            throw new Exception("Archivo no encontrado");
        }

        GridFsResource resource = gridFsTemplate.getResource(file);
        return resource.getInputStream().readAllBytes();
    }

    private void validarArchivo(TipoContenido tipoContenido, MultipartFile archivo) throws Exception {
        String contentType = archivo.getContentType();

        if (contentType == null) {
            throw new Exception("No se pudo determinar el tipo de archivo");
        }

        Set<String> pdf = Set.of("application/pdf");
        Set<String> word = Set.of(
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );
        Set<String> ppt = Set.of(
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        );
        Set<String> imagen = Set.of(
                "image/png",
                "image/jpeg",
                "image/jpg",
                "image/webp"
        );
        Set<String> audio = Set.of(
                "audio/mpeg",
                "audio/mp3",
                "audio/wav"
        );
        Set<String> video = Set.of(
                "video/mp4",
                "video/mpeg",
                "video/quicktime"
        );

        boolean valido = switch (tipoContenido) {
            case PDF -> pdf.contains(contentType);
            case WORD -> word.contains(contentType);
            case PPT -> ppt.contains(contentType);
            case IMAGEN -> imagen.contains(contentType);
            case AUDIO -> audio.contains(contentType);
            case VIDEO -> video.contains(contentType);
        };

        if (!valido) {
            throw new Exception("El archivo no corresponde al tipo de contenido seleccionado");
        }

        long maxSize = 10 * 1024 * 1024; // 10 MB
        if (archivo.getSize() > maxSize) {
            throw new Exception("El archivo supera el tamaño máximo permitido de 10 MB");
        }
    }

    @Override
    public List<ContenidoAcademico> buscarContenidos(BuscarContenidoDTO dto) {
        List<ContenidoAcademico> contenidos = contenidoRepository.findAll();

        return contenidos.stream()
                .filter(c -> dto.tema() == null || dto.tema().isBlank() ||
                        (c.getTema() != null && c.getTema().name().equalsIgnoreCase(dto.tema())))
                .filter(c -> dto.tipoContenido() == null || dto.tipoContenido().isBlank() ||
                        (c.getTipoContenido() != null && c.getTipoContenido().name().equalsIgnoreCase(dto.tipoContenido())))
                .filter(c -> dto.autor() == null || dto.autor().isBlank() ||
                        (c.getAutor() != null && c.getAutor().toLowerCase().contains(dto.autor().toLowerCase())))
                .filter(c -> dto.textoBusqueda() == null || dto.textoBusqueda().isBlank() ||
                        (c.getTitulo() != null && c.getTitulo().toLowerCase().contains(dto.textoBusqueda().toLowerCase())))
                .toList();
    }
}