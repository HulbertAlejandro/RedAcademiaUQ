package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearChatDTO(
        @NotBlank(message = "El ID del estudiante 1 es obligatorio")
        String estudiante1Id,
        
        @NotBlank(message = "El ID del estudiante 2 es obligatorio")
        String estudiante2Id
) {}
