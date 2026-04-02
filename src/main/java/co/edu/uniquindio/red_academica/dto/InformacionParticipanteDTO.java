package co.edu.uniquindio.red_academica.dto;

import java.time.LocalDateTime;

public record InformacionParticipanteDTO(
        String estudianteId,
        String nombre,
        String email,
        LocalDateTime fechaUnion
) {}
