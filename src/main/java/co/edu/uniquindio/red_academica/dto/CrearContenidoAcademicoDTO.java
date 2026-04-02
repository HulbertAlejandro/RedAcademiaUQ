package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearContenidoAcademicoDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
        String titulo,
        
        @NotBlank(message = "El tema es obligatorio")
        TEMA tema,
        
        @NotBlank(message = "El autor es obligatorio")
        String autor,
        
        @NotBlank(message = "El contenido es obligatorio")
        @Size(min = 10, max = 10000, message = "El contenido debe tener entre 10 y 10000 caracteres")
        String contenido,
        
        @NotBlank(message = "El tipo de contenido es obligatorio")
        TipoContenido tipoContenido
) {}
