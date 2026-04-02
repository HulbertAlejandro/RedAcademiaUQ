package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import java.time.LocalDateTime;
import java.util.List;

public record InformacionGrupoEstudioDTO(
        String id,
        String nombre,
        TEMA tema,
        String descripcion,
        List<InformacionParticipanteDTO> participantes,
        int cantidadParticipantes,
        LocalDateTime fechaCreacion,
        boolean esParticipante
) {}
