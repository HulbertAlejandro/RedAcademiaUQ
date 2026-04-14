package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crear(@RequestBody CrearChatDTO dto) throws Exception {
        String id = chatService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Chat creado exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionChatDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionChatDTO chat = chatService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Chat encontrado", chat));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<ResponseDTO<List<InformacionChatDTO>>> obtenerPorEstudiante(@PathVariable String estudianteId) throws Exception {
        List<InformacionChatDTO> chats = chatService.obtenerPorEstudiante(estudianteId);
        return ResponseEntity.ok(new ResponseDTO<>("Chats del estudiante", chats));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        chatService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Chat eliminado", null));
    }

    @PostMapping("/mensaje")
    public ResponseEntity<ResponseDTO<String>> enviarMensaje(@RequestBody CrearMensajeDTO dto) throws Exception {
        chatService.enviarMensaje(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Mensaje enviado", null));
    }

    @GetMapping("/entre/{estudiante1Id}/{estudiante2Id}")
    public ResponseEntity<ResponseDTO<List<InformacionChatDTO>>> obtenerChatsEntreEstudiantes(@PathVariable String estudiante1Id, @PathVariable String estudiante2Id) throws Exception {
        List<InformacionChatDTO> chats = chatService.obtenerChatsEntreEstudiantes(estudiante1Id, estudiante2Id);
        return ResponseEntity.ok(new ResponseDTO<>("Chats entre estudiantes", chats));
    }
}