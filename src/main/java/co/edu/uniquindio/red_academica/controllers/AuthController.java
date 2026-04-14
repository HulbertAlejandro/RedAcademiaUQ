package co.edu.uniquindio.red_academica.controllers;


import co.edu.uniquindio.red_academica.servicios.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @GetMapping("/existe/{correo}")
    public ResponseEntity<Boolean> existePorCorreo(@PathVariable String correo) {
        boolean existe = usuarioService.existePorCorreo(correo);
        return ResponseEntity.ok(existe);
    }

    @DeleteMapping("eliminar-id/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
        usuarioService.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado");
    }

}
