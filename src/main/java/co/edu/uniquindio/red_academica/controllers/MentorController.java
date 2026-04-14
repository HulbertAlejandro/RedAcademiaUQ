package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentores")
public class MentorController {

    private final MentorService mentorService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crear(@RequestBody CrearMentorDTO dto) throws Exception {
        String id = mentorService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Mentor creado exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionMentorDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionMentorDTO mentor = mentorService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Mentor encontrado", mentor));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<InformacionMentorDTO>>> obtenerTodos() {
        List<InformacionMentorDTO> mentores = mentorService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDTO<>("Lista de mentores", mentores));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionMentorDTO>> actualizar(@PathVariable String id, @RequestBody CrearMentorDTO dto) throws Exception {
        InformacionMentorDTO mentor = mentorService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Mentor actualizado", mentor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        mentorService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Mentor eliminado", null));
    }

    @GetMapping("/buscar/especialidad")
    public ResponseEntity<ResponseDTO<List<InformacionMentorDTO>>> buscarPorEspecialidad(@RequestParam String especialidad) throws Exception {
        List<InformacionMentorDTO> mentores = mentorService.buscarPorEspecialidad(especialidad);
        return ResponseEntity.ok(new ResponseDTO<>("Mentores por especialidad", mentores));
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<ResponseDTO<List<InformacionMentorDTO>>> buscarPorNombre(@RequestParam String nombre) throws Exception {
        List<InformacionMentorDTO> mentores = mentorService.buscarPorNombre(nombre);
        return ResponseEntity.ok(new ResponseDTO<>("Mentores por nombre", mentores));
    }
}