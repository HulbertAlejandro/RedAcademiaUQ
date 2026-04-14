package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crear(@RequestBody CrearEstudianteDTO dto) throws Exception {
        String id = estudianteService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiante creado exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionEstudianteDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionEstudianteDTO estudiante = estudianteService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiante encontrado", estudiante));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<InformacionEstudianteDTO>>> obtenerTodos() {
        List<InformacionEstudianteDTO> estudiantes = estudianteService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDTO<>("Lista de estudiantes", estudiantes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionEstudianteDTO>> actualizar(@PathVariable String id, @RequestBody CrearEstudianteDTO dto) throws Exception {
        InformacionEstudianteDTO estudiante = estudianteService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiante actualizado", estudiante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        estudianteService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiante eliminado", null));
    }

    @PostMapping("/{estudianteId}/amigos/{amigoId}")
    public ResponseEntity<ResponseDTO<String>> agregarAmigo(@PathVariable String estudianteId, @PathVariable String amigoId) throws Exception {
        estudianteService.agregarAmigo(estudianteId, amigoId);
        return ResponseEntity.ok(new ResponseDTO<>("Amigo agregado", null));
    }

    @DeleteMapping("/{estudianteId}/amigos/{amigoId}")
    public ResponseEntity<ResponseDTO<String>> eliminarAmigo(@PathVariable String estudianteId, @PathVariable String amigoId) throws Exception {
        estudianteService.eliminarAmigo(estudianteId, amigoId);
        return ResponseEntity.ok(new ResponseDTO<>("Amigo eliminado", null));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseDTO<List<InformacionEstudianteDTO>>> buscarPorNombre(@RequestParam String nombre) throws Exception {
        List<InformacionEstudianteDTO> estudiantes = estudianteService.buscarPorNombre(nombre);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiantes encontrados", estudiantes));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<ResponseDTO<List<InformacionEstudianteDTO>>> obtenerPorNivel(@PathVariable String nivel) throws Exception {
        // Asumiendo que nivel es un enum, convertir
        co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion nivelEnum = co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion.valueOf(nivel.toUpperCase());
        List<InformacionEstudianteDTO> estudiantes = estudianteService.obtenerPorNivel(nivelEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiantes por nivel", estudiantes));
    }

    @PostMapping("/{estudianteId}/puntos")
    public ResponseEntity<ResponseDTO<String>> agregarPuntosParticipacion(@PathVariable String estudianteId, @RequestParam int puntos) throws Exception {
        estudianteService.agregarPuntosParticipacion(estudianteId, puntos);
        return ResponseEntity.ok(new ResponseDTO<>("Puntos agregados", null));
    }
}