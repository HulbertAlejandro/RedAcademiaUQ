package co.edu.uniquindio.red_academica.dto;

public record InformacionEstudianteDTO(
        String id,
        String cedula,
        String nombre,
        String email,
        String programa,
        String estado
) {}
