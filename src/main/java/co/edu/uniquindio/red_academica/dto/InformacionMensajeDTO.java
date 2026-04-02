package co.edu.uniquindio.red_academica.dto;

import java.time.LocalDateTime;

public record InformacionMensajeDTO(
        String id,
        String remitenteId,
        String nombreRemitente,
        String destinatarioId,
        String contenido,
        LocalDateTime fecha,
        boolean esPropio
) {}
