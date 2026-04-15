package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ObtenerNombreDTO(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        String correo
) {
}
