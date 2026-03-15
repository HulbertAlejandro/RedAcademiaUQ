package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CrearEstudianteDTO(
        @NotBlank(message = "La cédula es obligatoria")
        @Size(min = 5, max = 20, message = "La cédula debe tener entre 5 y 20 caracteres")
        String cedula,
        
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,
        
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        String email,
        
        @NotBlank(message = "El programa es obligatorio")
        @Size(min = 3, max = 100, message = "El programa debe tener entre 3 y 100 caracteres")
        String programa,
        
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
        String password
) {}
