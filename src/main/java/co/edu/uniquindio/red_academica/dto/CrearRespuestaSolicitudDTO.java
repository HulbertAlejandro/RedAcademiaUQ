package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearRespuestaSolicitudDTO(

        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String solicitudId,

        @NotBlank(message = "El ID del autor es obligatorio")
        String autorId,

        @NotBlank(message = "El nombre del autor es obligatorio")
        String autorNombre,

        @Size(max = 300, message = "El comentario no puede superar 300 caracteres")
        String comentario,

        @NotBlank(message = "El texto de respuesta es obligatorio")
        @Size(min = 5, max = 5000, message = "La respuesta debe tener entre 5 y 5000 caracteres")
        String textoRespuesta,

        String contenidoAcademicoId,

        boolean esRespuestaFinal
) {}