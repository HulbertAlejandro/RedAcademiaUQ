package co.edu.uniquindio.red_academica.dto;

public record InformacionEstudianteDTO(
        String id,
        String nombre,
        String email,
        String nivel,
        int puntosParticipacion,
        int cantidadAmigos,
        int cantidadGrupos,
        int cantidadContenidos
) {}
