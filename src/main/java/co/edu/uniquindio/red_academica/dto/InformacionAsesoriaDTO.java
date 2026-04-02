package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
import java.time.LocalDateTime;

public record InformacionAsesoriaDTO(
        String id,
        String solicitanteId,
        String nombreSolicitante,
        String asesorId,
        String nombreAsesor,
        String tema,
        LocalDateTime fechaHora,
        String descripcion,
        String medio,
        EstadoAsesoria estado
) {}
