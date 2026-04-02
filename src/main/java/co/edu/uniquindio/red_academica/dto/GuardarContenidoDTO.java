package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record GuardarContenidoDTO(
        @NotBlank(message = "El ID del estudiante es obligatorio")
        String estudianteId,
        
        @NotBlank(message = "El ID del contenido es obligatorio")
        String contenidoId
) {}
