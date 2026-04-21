package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearRespuestaSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.InformacionAdjuntoRespuestaDTO;
import co.edu.uniquindio.red_academica.dto.InformacionRespuestaSolicitudDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.AdjuntoRespuesta;
import co.edu.uniquindio.red_academica.modelo.documentos.RespuestaSolicitud;
import co.edu.uniquindio.red_academica.modelo.documentos.SolicitudAyuda;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
import co.edu.uniquindio.red_academica.repositorios.RespuestaSolicitudRepository;
import co.edu.uniquindio.red_academica.repositorios.SolicitudAyudaRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.RespuestaSolicitudService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RespuestaSolicitudServiceImpl implements RespuestaSolicitudService {

    private final RespuestaSolicitudRepository respuestaRepository;
    private final SolicitudAyudaRepository solicitudRepository;
    private final GridFsTemplate gridFsTemplate;

    @Override
    public String crearRespuesta(CrearRespuestaSolicitudDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(dto.solicitudId())
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        RespuestaSolicitud respuesta = RespuestaSolicitud.builder()
                .id(java.util.UUID.randomUUID().toString())
                .solicitudId(dto.solicitudId())
                .autorId(dto.autorId())
                .autorNombre(dto.autorNombre())
                .comentario(dto.comentario())
                .textoRespuesta(dto.textoRespuesta())
                .contenidoAcademicoId(dto.contenidoAcademicoId())
                .adjuntos(new ArrayList<>())
                .fechaCreacion(LocalDateTime.now())
                .esRespuestaFinal(dto.esRespuestaFinal())
                .build();

        respuestaRepository.save(respuesta);

        if (solicitud.getEstado() == EstadoSolicitud.ABIERTA) {
            solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        }

        if (dto.contenidoAcademicoId() != null && !dto.contenidoAcademicoId().isBlank()) {
            solicitud.setIdContenidoResuelto(dto.contenidoAcademicoId());
        }

        solicitudRepository.save(solicitud);

        return respuesta.getId();
    }

    @Override
    public void subirAdjunto(String respuestaId, MultipartFile archivo) throws Exception {
        RespuestaSolicitud respuesta = respuestaRepository.findById(respuestaId)
                .orElseThrow(() -> new Exception("Respuesta no encontrada"));

        ObjectId archivoId = gridFsTemplate.store(
                archivo.getInputStream(),
                archivo.getOriginalFilename(),
                archivo.getContentType()
        );

        AdjuntoRespuesta adjunto = AdjuntoRespuesta.builder()
                .nombreArchivo(archivo.getOriginalFilename())
                .contentType(archivo.getContentType())
                .tamanoBytes(archivo.getSize())
                .archivoId(archivoId.toHexString())
                .build();

        if (respuesta.getAdjuntos() == null) {
            respuesta.setAdjuntos(new ArrayList<>());
        }

        respuesta.getAdjuntos().add(adjunto);
        respuestaRepository.save(respuesta);
    }

    @Override
    public List<InformacionRespuestaSolicitudDTO> obtenerPorSolicitud(String solicitudId) throws Exception {
        return respuestaRepository.findBySolicitudIdOrderByFechaCreacionAsc(solicitudId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public InformacionRespuestaSolicitudDTO obtenerPorId(String respuestaId) throws Exception {
        RespuestaSolicitud respuesta = respuestaRepository.findById(respuestaId)
                .orElseThrow(() -> new Exception("Respuesta no encontrada"));

        return mapToDTO(respuesta);
    }

    @Override
    public void marcarRespuestaFinal(String respuestaId) throws Exception {
        RespuestaSolicitud respuesta = respuestaRepository.findById(respuestaId)
                .orElseThrow(() -> new Exception("Respuesta no encontrada"));

        respuesta.setEsRespuestaFinal(true);
        respuestaRepository.save(respuesta);
    }

    private InformacionRespuestaSolicitudDTO mapToDTO(RespuestaSolicitud respuesta) {
        List<InformacionAdjuntoRespuestaDTO> adjuntos = respuesta.getAdjuntos() == null
                ? List.of()
                : respuesta.getAdjuntos().stream()
                .map(a -> new InformacionAdjuntoRespuestaDTO(
                        a.getNombreArchivo(),
                        a.getContentType(),
                        a.getTamanoBytes(),
                        a.getArchivoId()
                ))
                .toList();

        return new InformacionRespuestaSolicitudDTO(
                respuesta.getId(),
                respuesta.getSolicitudId(),
                respuesta.getAutorId(),
                respuesta.getAutorNombre(),
                respuesta.getComentario(),
                respuesta.getTextoRespuesta(),
                respuesta.getContenidoAcademicoId(),
                adjuntos,
                respuesta.getFechaCreacion(),
                respuesta.isEsRespuestaFinal()
        );
    }
}