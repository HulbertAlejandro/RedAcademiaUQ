package co.edu.uniquindio.red_academica.dto;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;

public record BuscarContenidoDTO(
        TEMA tema,
        TipoContenido tipoContenido,
        String autor,
        String textoBusqueda,
        int pagina,
        int tamaño
) {}
