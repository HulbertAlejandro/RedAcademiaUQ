package co.edu.uniquindio.red_academica.dto;

import java.util.List;

public record InformacionMentorDTO(
        String id,
        String cedula,
        String nombre,
        String email,
        String especialidad,
        List<String> horariosDisponibles
) {}
