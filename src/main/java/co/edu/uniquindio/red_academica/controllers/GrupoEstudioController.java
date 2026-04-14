package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.GrupoEstudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grupos-estudio")
public class GrupoEstudioController {

    private final GrupoEstudioService grupoEstudioService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crear(@RequestBody CrearGrupoEstudioDTO dto) throws Exception {
        String id = grupoEstudioService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Grupo de estudio creado exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionGrupoEstudioDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionGrupoEstudioDTO grupo = grupoEstudioService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Grupo encontrado", grupo));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<InformacionGrupoEstudioDTO>>> obtenerTodos() {
        List<InformacionGrupoEstudioDTO> grupos = grupoEstudioService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDTO<>("Lista de grupos", grupos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionGrupoEstudioDTO>> actualizar(@PathVariable String id, @RequestBody CrearGrupoEstudioDTO dto) throws Exception {
        InformacionGrupoEstudioDTO grupo = grupoEstudioService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Grupo actualizado", grupo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        grupoEstudioService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Grupo eliminado", null));
    }

    @GetMapping("/buscar/tema/{tema}")
    public ResponseEntity<ResponseDTO<List<InformacionGrupoEstudioDTO>>> buscarPorTema(@PathVariable String tema) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.TEMA temaEnum = co.edu.uniquindio.red_academica.modelo.enums.TEMA.valueOf(tema.toUpperCase());
        List<InformacionGrupoEstudioDTO> grupos = grupoEstudioService.buscarPorTema(temaEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Grupos por tema", grupos));
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<ResponseDTO<List<InformacionGrupoEstudioDTO>>> buscarPorNombre(@RequestParam String nombre) throws Exception {
        List<InformacionGrupoEstudioDTO> grupos = grupoEstudioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(new ResponseDTO<>("Grupos por nombre", grupos));
    }

    @PostMapping("/unirse")
    public ResponseEntity<ResponseDTO<String>> unirseGrupo(@RequestBody UnirseGrupoDTO dto) throws Exception {
        grupoEstudioService.unirseGrupo(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Unido al grupo", null));
    }

    @PostMapping("/abandonar")
    public ResponseEntity<ResponseDTO<String>> abandonarGrupo(@RequestBody AbandonarGrupoDTO dto) throws Exception {
        grupoEstudioService.abandonarGrupo(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Abandonado el grupo", null));
    }

    @PostMapping("/rechazar")
    public ResponseEntity<ResponseDTO<String>> rechazarInvitacion(@RequestBody RechazarGrupoDTO dto) throws Exception {
        grupoEstudioService.rechazarInvitacion(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Invitación rechazada", null));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<ResponseDTO<List<InformacionGrupoEstudioDTO>>> obtenerGruposDeEstudiante(@PathVariable String estudianteId) throws Exception {
        List<InformacionGrupoEstudioDTO> grupos = grupoEstudioService.obtenerGruposDeEstudiante(estudianteId);
        return ResponseEntity.ok(new ResponseDTO<>("Grupos del estudiante", grupos));
    }
}