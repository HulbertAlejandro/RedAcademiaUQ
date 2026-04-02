package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
import jakarta.validation.constraints.NotBlank;

public record ActualizarEstadoDTO(
        @NotBlank(message = "El ID es obligatorio")
        String id,
        
        @NotBlank(message = "El estado es obligatorio")
        EstadoAsesoria estado
) {}
