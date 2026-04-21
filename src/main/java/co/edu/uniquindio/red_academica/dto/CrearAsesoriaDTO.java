package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record CrearAsesoriaDTO(
        @NotBlank(message = "El ID del solicitante es obligatorio")
        String solicitanteId,

        @NotBlank(message = "El ID del asesor es obligatorio")
        String asesorId,

        @NotBlank(message = "El tema es obligatorio")
        @Size(min = 3, max = 200, message = "El tema debe tener entre 3 y 200 caracteres")
        String tema,

        @NotNull(message = "La fecha y hora son obligatorias")
        LocalDateTime fechaHora,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion,

        @Size(max = 100, message = "El medio no puede exceder 100 caracteres")
        String medio
) {}