package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;

public record EstadisticaSolicitudDTO(
        EstadoSolicitud estado,
        int total
) {
}
