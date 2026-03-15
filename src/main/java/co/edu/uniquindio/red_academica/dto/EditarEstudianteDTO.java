package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditarEstudianteDTO(
        @NotBlank(message = "El ID del estudiante es obligatorio")
        String id,
        
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,
        
        @NotBlank(message = "El programa es obligatorio")
        @Size(min = 3, max = 100, message = "El programa debe tener entre 3 y 100 caracteres")
        String programa
) {}
