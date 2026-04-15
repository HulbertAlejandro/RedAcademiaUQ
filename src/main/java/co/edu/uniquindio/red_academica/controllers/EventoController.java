package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/publico/evento")
public class EventoController {

    @GetMapping("/obtener-todos/{pagina}")
    public ResponseEntity<ResponseDTO<List<Object>>> obtenerTodos(@PathVariable int pagina) {
        // Endpoint placeholder for frontend compatibility.
        // Replace with real evento retrieval logic when the event model is implemented.
        return ResponseEntity.ok(ResponseDTO.exito(List.of()));
    }
}