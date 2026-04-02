package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearGrupoEstudioDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,
        
        @NotBlank(message = "El tema es obligatorio")
        TEMA tema,
        
        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
        String descripcion
) {}
