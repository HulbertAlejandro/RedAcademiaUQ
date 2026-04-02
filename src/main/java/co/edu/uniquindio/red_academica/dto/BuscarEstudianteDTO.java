package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.Size;

public record BuscarEstudianteDTO(
        @Size(max = 100, message = "El texto de búsqueda no puede exceder 100 caracteres")
        String textoBusqueda,
        
        int pagina,
        int tamaño
) {}
