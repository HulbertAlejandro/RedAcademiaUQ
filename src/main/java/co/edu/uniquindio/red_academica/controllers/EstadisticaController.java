package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    @GetMapping("/materias-mas-solicitadas")
    public ResponseEntity<ResponseDTO<List<EstadisticaMateriaDTO>>> materiasMasSolicitadas() {

        List<EstadisticaMateriaDTO> materias = estadisticaService.materiasMasSolicitadas();

        return ResponseEntity.ok(
                new ResponseDTO<>("Materias más solicitadas", materias)
        );
    }

    @GetMapping("/asesores-mas-activos")
    public ResponseEntity<ResponseDTO<List<EstadisticaAsesorDTO>>> asesoresMasActivos() {

        List<EstadisticaAsesorDTO> asesores = estadisticaService.asesoresMasActivos();

        return ResponseEntity.ok(
                new ResponseDTO<>("Asesores más activos", asesores)
        );
    }

    @GetMapping("/asesorias-estado")
    public ResponseEntity<ResponseDTO<List<EstadisticaAsesoriaDTO>>> asesoriasPorEstado() {

        List<EstadisticaAsesoriaDTO> asesorias = estadisticaService.asesoriasPorEstado();

        return ResponseEntity.ok(
                new ResponseDTO<>("Asesorías agrupadas por estado", asesorias)
        );
    }

    @GetMapping("/solicitudes-estado")
    public ResponseEntity<ResponseDTO<List<EstadisticaSolicitudDTO>>> solicitudesPorEstado() {

        List<EstadisticaSolicitudDTO> solicitudes = estadisticaService.solicitudesPorEstado();

        return ResponseEntity.ok(
                new ResponseDTO<>("Solicitudes agrupadas por estado", solicitudes)
        );
    }
}