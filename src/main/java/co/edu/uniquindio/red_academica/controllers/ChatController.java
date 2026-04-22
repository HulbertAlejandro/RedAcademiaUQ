package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.ChatService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponseDTO<String>> crear(@Valid @RequestBody CrearChatDTO dto) throws Exception {
        String id = chatService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Chat creado exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionChatDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionChatDTO chat = chatService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Chat encontrado", chat));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ResponseDTO<List<InformacionChatDTO>>> obtenerPorUsuario(@PathVariable String usuarioId) throws Exception {
        List<InformacionChatDTO> chats = chatService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(new ResponseDTO<>("Chats del usuario", chats));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        chatService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Chat eliminado", null));
    }

    @PostMapping("/mensaje")
    public ResponseEntity<ResponseDTO<String>> enviarMensaje(@Valid @RequestBody CrearMensajeDTO dto) throws Exception {
        chatService.enviarMensaje(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Mensaje enviado", null));
    }

    @GetMapping("/entre/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<ResponseDTO<List<InformacionChatDTO>>> obtenerChatsEntreUsuarios(
            @PathVariable String usuario1Id,
            @PathVariable String usuario2Id
    ) throws Exception {
        List<InformacionChatDTO> chats = chatService.obtenerChatsEntreUsuarios(usuario1Id, usuario2Id);
        return ResponseEntity.ok(new ResponseDTO<>("Chats entre usuarios", chats));
    }
}