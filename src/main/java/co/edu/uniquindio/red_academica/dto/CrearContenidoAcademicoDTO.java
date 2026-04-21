package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearContenidoAcademicoDTO(

        @NotBlank(message = "El título es obligatorio")
        @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
        String titulo,

        @NotNull(message = "El tema es obligatorio")
        TEMA tema,

        @NotBlank(message = "El autor es obligatorio")
        @Size(min = 3, max = 100, message = "El autor debe tener entre 3 y 100 caracteres")
        String autor,

        @NotNull(message = "El tipo de contenido es obligatorio")
        TipoContenido tipoContenido
) {}