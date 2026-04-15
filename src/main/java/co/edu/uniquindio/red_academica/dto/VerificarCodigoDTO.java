package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificarCodigoDTO(
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "El código es obligatorio")
        String codigo
) {
}
