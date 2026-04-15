package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestablecerPasswordDTO(
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "El código es obligatorio")
        String codigo,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 6, max = 20, message = "La nueva contraseña debe tener entre 6 y 20 caracteres")
        String passwordNueva
) {
}
