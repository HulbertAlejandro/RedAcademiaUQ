package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record CerrarSolicitudDTO(
        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String solicitudId,

        @NotBlank(message = "El ID del solicitante es obligatorio")
        String solicitanteId
) {}