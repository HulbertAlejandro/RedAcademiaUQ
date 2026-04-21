package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.BuscarContenidoDTO;
import co.edu.uniquindio.red_academica.dto.CrearContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.dto.MensajeDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.ContenidoAcademico;
import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import co.edu.uniquindio.red_academica.servicios.interfaces.ContenidoAcademicoService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos-academicos")
@RequiredArgsConstructor
public class ContenidoAcademicoController {

    private final ContenidoAcademicoService contenidoService;
    private final GridFsTemplate gridFsTemplate;

    @PostMapping(value = "/subir", consumes = "multipart/form-data")
    public ResponseEntity<MensajeDTO<String>> subirContenido(
            @RequestParam("titulo") String titulo,
            @RequestParam("tema") TEMA tema,
            @RequestParam("autor") String autor,
            @RequestParam("tipoContenido") TipoContenido tipoContenido,
            @RequestParam("archivo") MultipartFile archivo
    ) throws Exception {

        CrearContenidoAcademicoDTO dto = new CrearContenidoAcademicoDTO(
                titulo, tema, autor, tipoContenido
        );

        contenidoService.subirContenido(dto, archivo);

        return ResponseEntity.ok(
                new MensajeDTO<>(false, "Contenido académico subido correctamente")
        );
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<MensajeDTO<ContenidoAcademico>> obtenerContenido(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(
                new MensajeDTO<>(false, contenidoService.obtenerContenidoPorId(id))
        );
    }

    @GetMapping("/obtener-contenidos")
    public ResponseEntity<MensajeDTO<List<ContenidoAcademico>>> obtenerTodosContenidos() {
        return ResponseEntity.ok(
                new MensajeDTO<>(false, contenidoService.obtenerTodosContenidos())
        );
    }

    @GetMapping("/archivo/{id}")
    public ResponseEntity<Resource> obtenerArchivo(@PathVariable String id) throws Exception {

        ContenidoAcademico contenido = contenidoService.obtenerContenidoPorId(id);

        if (contenido.getArchivoId() == null || contenido.getArchivoId().isBlank()) {
            throw new Exception("El contenido no tiene un archivo asociado");
        }

        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(contenido.getArchivoId())))
        );

        if (file == null) {
            throw new Exception("Archivo no encontrado");
        }

        GridFsResource resource = gridFsTemplate.getResource(file);

        String contentType = "application/octet-stream";
        if (file.getMetadata() != null) {
            if (file.getMetadata().get("_contentType") != null) {
                contentType = file.getMetadata().getString("_contentType");
            } else if (file.getMetadata().get("contentType") != null) {
                contentType = file.getMetadata().getString("contentType");
            }
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/buscar")
    public ResponseEntity<MensajeDTO<List<ContenidoAcademico>>> buscarContenidos(@RequestBody BuscarContenidoDTO dto) {
        return ResponseEntity.ok(
                new MensajeDTO<>(false, contenidoService.buscarContenidos(dto))
        );
    }
}