package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import java.time.LocalDateTime;
import java.util.List;

public record InformacionContenidoAcademicoDTO(
        String id,
        String titulo,
        TEMA tema,
        String autor,
        String contenido,
        TipoContenido tipoContenido,
        List<InformacionValoracionDTO> valoraciones,
        double puntuacionPromedio,
        LocalDateTime fechaCreacion
) {}
