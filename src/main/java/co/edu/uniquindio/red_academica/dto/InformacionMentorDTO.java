package co.edu.uniquindio.red_academica.dto;

public record InformacionMentorDTO(
        String id,
        String nombre,
        String email,
        String especialidad,
        int cantidadHorarios,
        int cantidadAsesorias
) {}
