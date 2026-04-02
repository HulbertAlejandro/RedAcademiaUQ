package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import java.time.LocalDateTime;

public record InformacionSolicitudAyudaDTO(
        String id,
        TEMA tema,
        int urgencia,
        String solicitanteId,
        String nombreSolicitante,
        String descripcion,
        EstadoSolicitud estado,
        LocalDateTime fechaCreacion,
        String idContenidoResuelto,
        String nombreContenidoResuelto
) {}
