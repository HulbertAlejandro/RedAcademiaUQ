package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.repositorios.AsesoriaRepository;
import co.edu.uniquindio.red_academica.repositorios.SolicitudAyudaRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadisticaServiceImpl implements EstadisticaService {

    private final AsesoriaRepository asesoriaRepository;
    private final SolicitudAyudaRepository solicitudAyudaRepository;

    @Override
    public List<EstadisticaMateriaDTO> materiasMasSolicitadas() {
        return asesoriaRepository.materiasMasSolicitadas();
    }

    @Override
    public List<EstadisticaAsesorDTO> asesoresMasActivos() {
        return asesoriaRepository.asesorConMasAsesorias();
    }

    @Override
    public List<EstadisticaAsesoriaDTO> asesoriasPorEstado() {
        return asesoriaRepository.asesoriasPorEstado();
    }

    @Override
    public List<EstadisticaSolicitudDTO> solicitudesPorEstado() {
        return solicitudAyudaRepository.solicitudesPorEstado();
    }
}