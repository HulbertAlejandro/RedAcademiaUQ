package co.edu.uniquindio.red_academica.dto;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionRespuestaSolicitudDTO(
        String id,
        String solicitudId,
        String autorId,
        String autorNombre,
        String comentario,
        String textoRespuesta,
        String contenidoAcademicoId,
        List<InformacionAdjuntoRespuestaDTO> adjuntos,
        LocalDateTime fechaCreacion,
        boolean esRespuestaFinal
) {}