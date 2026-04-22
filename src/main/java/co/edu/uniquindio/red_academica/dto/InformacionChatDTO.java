package co.edu.uniquindio.red_academica.dto;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionChatDTO(
        String id,
        String usuario1Id,
        String nombreUsuario1,
        String usuario2Id,
        String nombreUsuario2,
        List<InformacionMensajeDTO> mensajes,
        LocalDateTime ultimoMensaje
) {}