package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.EstadisticaAsesorDTO;
import co.edu.uniquindio.red_academica.dto.EstadisticaAsesoriaDTO;
import co.edu.uniquindio.red_academica.dto.EstadisticaMateriaDTO;
import co.edu.uniquindio.red_academica.dto.EstadisticaSolicitudDTO;

import java.util.List;

public interface EstadisticaService {

    List<EstadisticaMateriaDTO> materiasMasSolicitadas();

    List<EstadisticaAsesorDTO> asesoresMasActivos();

    List<EstadisticaAsesoriaDTO> asesoriasPorEstado();

    List<EstadisticaSolicitudDTO> solicitudesPorEstado();
}