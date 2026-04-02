package co.edu.uniquindio.red_academica.dto;

import jakarta.validation.constraints.NotBlank;

public record ParticipanteGrupoDTO(
        @NotBlank(message = "El ID del grupo es obligatorio")
        String grupoId,
        
        @NotBlank(message = "El ID del estudiante es obligatorio")
        String estudianteId
) {}
