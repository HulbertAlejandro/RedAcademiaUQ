package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.TipoRecurso;
import java.time.LocalDateTime;

public record InformacionRecursoAcademicoDTO(
        String id,
        String nombre,
        String descripcion,
        TipoRecurso tipo,
        String urlArchivo,
        LocalDateTime fechaCreacion,
        String idAutor
) {}
