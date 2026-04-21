package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import jakarta.validation.constraints.*;

public record CrearSolicitudAyudaDTO(
        @NotNull(message = "El tema es obligatorio")
        TEMA tema,

        @Min(value = 1, message = "La urgencia mínima es 1")
        @Max(value = 5, message = "La urgencia máxima es 5")
        int urgencia,

        @NotBlank(message = "El ID del solicitante es obligatorio")
        String solicitanteId,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
        String descripcion
) {}