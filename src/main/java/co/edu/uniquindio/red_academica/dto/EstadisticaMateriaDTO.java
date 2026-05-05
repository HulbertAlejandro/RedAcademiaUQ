package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TEMA;

public record EstadisticaMateriaDTO(
        TEMA tema,
        int total
) { }
