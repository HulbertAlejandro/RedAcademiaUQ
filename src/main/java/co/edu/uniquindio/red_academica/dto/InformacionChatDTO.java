package co.edu.uniquindio.red_academica.dto;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionChatDTO(
        String id,
        String estudiante1Id,
        String nombreEstudiante1,
        String estudiante2Id,
        String nombreEstudiante2,
        List<InformacionMensajeDTO> mensajes,
        LocalDateTime ultimoMensaje
) {}
