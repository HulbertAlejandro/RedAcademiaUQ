package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record ResolverSolicitudDTO(
        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String solicitudId,
        
        @NotBlank(message = "El ID del contenido resuelto es obligatorio")
        String contenidoId
) {}
