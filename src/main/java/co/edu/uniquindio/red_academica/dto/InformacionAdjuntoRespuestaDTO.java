package co.edu.uniquindio.red_academica.dto;

public record InformacionAdjuntoRespuestaDTO(
        String nombreArchivo,
        String contentType,
        Long tamanoBytes,
        String archivoId
) {}