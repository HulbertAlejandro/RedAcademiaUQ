package co.edu.uniquindio.red_academica.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BuscarContenidoDTO(
        String tema,
        String tipoContenido,
        String autor,
        String textoBusqueda,
        int pagina,
        @JsonAlias({"tamaño", "tamano"})
        int tamano
) {}

