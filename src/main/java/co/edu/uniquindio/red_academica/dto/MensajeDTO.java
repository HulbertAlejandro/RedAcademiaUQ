package co.edu.uniquindio.red_academica.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
