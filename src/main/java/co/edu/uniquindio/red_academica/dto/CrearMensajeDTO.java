package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearMensajeDTO(
        @NotBlank(message = "El ID del remitente es obligatorio")
        String remitenteId,
        
        @NotBlank(message = "El ID del destinatario es obligatorio")
        String destinatarioId,
        
        @NotBlank(message = "El ID del chat es obligatorio")
        String chatId,
        
        @NotBlank(message = "El contenido es obligatorio")
        @Size(min = 1, max = 1000, message = "El contenido debe tener entre 1 y 1000 caracteres")
        String contenido
) {}
