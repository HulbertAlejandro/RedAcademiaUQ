package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearChatDTO(
        @NotBlank(message = "El ID del usuario 1 es obligatorio")
        String usuario1Id,

        @NotBlank(message = "El ID del usuario 2 es obligatorio")
        String usuario2Id
) {}