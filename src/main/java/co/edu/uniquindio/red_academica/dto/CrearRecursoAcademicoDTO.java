package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.TipoRecurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearRecursoAcademicoDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
        String nombre,
        
        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
        String descripcion,
        
        @NotBlank(message = "El tipo de recurso es obligatorio")
        TipoRecurso tipo,
        
        @NotBlank(message = "La URL del archivo es obligatoria")
        String urlArchivo,
        
        @NotBlank(message = "El ID del autor es obligatorio")
        String idAutor
) {}
