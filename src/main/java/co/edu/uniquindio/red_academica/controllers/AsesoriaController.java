package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.AsesoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asesorias")
public class AsesoriaController {

    private final AsesoriaService asesoriaService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crear(@RequestBody CrearAsesoriaDTO dto) throws Exception {
        String id = asesoriaService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Asesoría creada exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionAsesoriaDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionAsesoriaDTO asesoria = asesoriaService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Asesoría encontrada", asesoria));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<InformacionAsesoriaDTO>>> obtenerTodos() {
        List<InformacionAsesoriaDTO> asesorias = asesoriaService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDTO<>("Lista de asesorías", asesorias));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionAsesoriaDTO>> actualizar(@PathVariable String id, @RequestBody CrearAsesoriaDTO dto) throws Exception {
        InformacionAsesoriaDTO asesoria = asesoriaService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Asesoría actualizada", asesoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        asesoriaService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Asesoría eliminada", null));
    }

    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<ResponseDTO<List<InformacionAsesoriaDTO>>> obtenerPorSolicitante(@PathVariable String solicitanteId) throws Exception {
        List<InformacionAsesoriaDTO> asesorias = asesoriaService.obtenerPorSolicitante(solicitanteId);
        return ResponseEntity.ok(new ResponseDTO<>("Asesorías del solicitante", asesorias));
    }

    @GetMapping("/asesor/{asesorId}")
    public ResponseEntity<ResponseDTO<List<InformacionAsesoriaDTO>>> obtenerPorAsesor(@PathVariable String asesorId) throws Exception {
        List<InformacionAsesoriaDTO> asesorias = asesoriaService.obtenerPorAsesor(asesorId);
        return ResponseEntity.ok(new ResponseDTO<>("Asesorías del asesor", asesorias));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ResponseDTO<List<InformacionAsesoriaDTO>>> obtenerPorEstado(@PathVariable String estado) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria estadoEnum = co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria.valueOf(estado.toUpperCase());
        List<InformacionAsesoriaDTO> asesorias = asesoriaService.obtenerPorEstado(estadoEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Asesorías por estado", asesorias));
    }

    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<ResponseDTO<String>> actualizarEstado(@PathVariable String id, @PathVariable String estado) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria estadoEnum = co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria.valueOf(estado.toUpperCase());
        asesoriaService.actualizarEstado(id, estadoEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Estado actualizado", null));
    }
}