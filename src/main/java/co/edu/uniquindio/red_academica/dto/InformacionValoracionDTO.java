package co.edu.uniquindio.red_academica.dto;

import java.time.LocalDateTime;

public record InformacionValoracionDTO(
        String estudianteId,
        String nombreEstudiante,
        int puntaje,
        String comentario,
        LocalDateTime fecha
) {}
