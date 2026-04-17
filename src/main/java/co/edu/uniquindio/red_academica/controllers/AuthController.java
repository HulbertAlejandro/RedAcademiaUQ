package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final EstudianteService estudianteService;

    @PostMapping("/crear-estudiante")
    public ResponseEntity<ResponseDTO<String>> crearCuenta(@RequestBody CrearEstudianteDTO dto) throws Exception {
        String id = estudianteService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiante creado exitosamente", id));
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<ResponseDTO<TokenDTO>> login(@RequestBody LoginDTO dto) throws Exception {
        TokenDTO estudiante = estudianteService.autenticar(dto);

        System.out.println("TOKEN DTO: " + estudiante);
        System.out.println("TOKEN: " + estudiante.token());

        ResponseDTO<TokenDTO> response = new ResponseDTO<>("Inicio de sesión exitoso", estudiante);
        System.out.println("RESPONSE DTO: " + response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<ResponseDTO<String>> recuperarPassword(@Valid @RequestBody RecuperarPasswordDTO dto) throws Exception {
        estudianteService.iniciarRecuperacionPassword(dto.email());
        return ResponseEntity.ok(new ResponseDTO<>("Código de recuperación enviado al correo", null));
    }

    @PostMapping("/verificar-codigo-recuperacion")
    public ResponseEntity<ResponseDTO<Boolean>> verificarCodigo(@Valid @RequestBody VerificarCodigoDTO dto) throws Exception {
        estudianteService.verificarCodigoRecuperacion(dto.email(), dto.codigo());
        return ResponseEntity.ok(new ResponseDTO<>("Código válido", true));
    }

    @PutMapping("/restablecer-password")
    public ResponseEntity<ResponseDTO<String>> restablecerPassword(@Valid @RequestBody RestablecerPasswordDTO dto) throws Exception {
        estudianteService.restablecerPassword(dto.email(), dto.codigo(), dto.passwordNueva());
        return ResponseEntity.ok(new ResponseDTO<>("Contraseña restablecida", null));
    }

    @PutMapping("/cambiar-contrasena/{estudianteId}")
    public ResponseEntity<ResponseDTO<String>> cambiarContrasena(@PathVariable String estudianteId, @RequestBody CambiarPasswordDTO dto) throws Exception {
        estudianteService.cambiarContrasena(estudianteId, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Contraseña actualizada", null));
    }

    @GetMapping("/existe/{correo}")
    public ResponseEntity<ResponseDTO<Boolean>> existePorCorreo(@PathVariable String correo) {
        boolean existe = estudianteService.existePorCorreo(correo);
        return ResponseEntity.ok(new ResponseDTO<>("Verificación de correo", existe));
    }

    @DeleteMapping("/eliminar-id/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        estudianteService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Estudiante eliminado", null));
    }

    @GetMapping("/obtener-nombre")
    public ResponseEntity<ResponseDTO<String>> obtenerNombre(@Valid @RequestBody ObtenerNombreDTO usuario) {
        String nombre = estudianteService.obtenerPorCorreo(usuario);
        return ResponseEntity.ok(new ResponseDTO<>("Nombre encontrado", nombre));
    }
}