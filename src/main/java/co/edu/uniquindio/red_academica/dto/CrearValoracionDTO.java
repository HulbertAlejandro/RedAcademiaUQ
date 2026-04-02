package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearValoracionDTO(
        @NotBlank(message = "El ID del estudiante es obligatorio")
        String estudianteId,
        
        @NotBlank(message = "El ID del contenido es obligatorio")
        String contenidoId,
        
        @Min(value = 1, message = "El puntaje mínimo es 1")
        @Max(value = 5, message = "El puntaje máximo es 5")
        int puntaje,
        
        @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
        String comentario
) {}
