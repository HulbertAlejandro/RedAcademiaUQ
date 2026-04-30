package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;

public record EstadisticaAsesoriaDTO(
        EstadoAsesoria estado,
        int total
) {
}
