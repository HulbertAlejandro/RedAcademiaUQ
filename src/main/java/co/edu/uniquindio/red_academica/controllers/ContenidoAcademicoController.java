package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.ContenidoAcademicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequiredArgsConstructor
@RequestMapping("/api/contenidos-academicos")
public class ContenidoAcademicoController {

    private final ContenidoAcademicoService contenidoAcademicoService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crear(@RequestBody CrearContenidoAcademicoDTO dto) throws Exception {
        String id = contenidoAcademicoService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Contenido académico creado exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionContenidoAcademicoDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionContenidoAcademicoDTO contenido = contenidoAcademicoService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Contenido encontrado", contenido));
    }

    @GetMapping("/obtener-contenidos")
    public ResponseEntity<ResponseDTO<List<InformacionContenidoAcademicoDTO>>> obtenerTodos() {
        List<InformacionContenidoAcademicoDTO> contenidos = contenidoAcademicoService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDTO<>("Lista de contenidos", contenidos));
    }

    @PostMapping("/buscar")
    public ResponseEntity<ResponseDTO<List<InformacionContenidoAcademicoDTO>>> buscar(@RequestBody BuscarContenidoDTO dto) throws Exception {
        List<InformacionContenidoAcademicoDTO> contenidos = contenidoAcademicoService.buscar(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Contenidos encontrados", contenidos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionContenidoAcademicoDTO>> actualizar(@PathVariable String id, @RequestBody CrearContenidoAcademicoDTO dto) throws Exception {
        InformacionContenidoAcademicoDTO contenido = contenidoAcademicoService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Contenido actualizado", contenido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        contenidoAcademicoService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Contenido eliminado", null));
    }

    @GetMapping("/buscar/tema/{tema}")
    public ResponseEntity<ResponseDTO<List<InformacionContenidoAcademicoDTO>>> buscarPorTema(@PathVariable String tema) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.TEMA temaEnum = co.edu.uniquindio.red_academica.modelo.enums.TEMA.valueOf(tema.toUpperCase());
        List<InformacionContenidoAcademicoDTO> contenidos = contenidoAcademicoService.buscarPorTema(temaEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Contenidos por tema", contenidos));
    }

    @GetMapping("/buscar/autor/{autorId}")
    public ResponseEntity<ResponseDTO<List<InformacionContenidoAcademicoDTO>>> buscarPorAutor(@PathVariable String autorId) throws Exception {
        List<InformacionContenidoAcademicoDTO> contenidos = contenidoAcademicoService.buscarPorAutor(autorId);
        return ResponseEntity.ok(new ResponseDTO<>("Contenidos por autor", contenidos));
    }

    @GetMapping("/buscar/tipo/{tipo}")
    public ResponseEntity<ResponseDTO<List<InformacionContenidoAcademicoDTO>>> buscarPorTipo(@PathVariable String tipo) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.TipoContenido tipoEnum = co.edu.uniquindio.red_academica.modelo.enums.TipoContenido.valueOf(tipo.toUpperCase());
        List<InformacionContenidoAcademicoDTO> contenidos = contenidoAcademicoService.buscarPorTipo(tipoEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Contenidos por tipo", contenidos));
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<ResponseDTO<List<InformacionContenidoAcademicoDTO>>> buscarPorTitulo(@RequestParam String titulo) throws Exception {
        List<InformacionContenidoAcademicoDTO> contenidos = contenidoAcademicoService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(new ResponseDTO<>("Contenidos por título", contenidos));
    }

    @PostMapping("/valoracion")
    public ResponseEntity<ResponseDTO<String>> agregarValoracion(@RequestBody CrearValoracionDTO dto) throws Exception {
        contenidoAcademicoService.agregarValoracion(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Valoración agregada", null));
    }

    @PostMapping("/guardar")
    public ResponseEntity<ResponseDTO<String>> guardarContenido(@RequestParam String estudianteId, @RequestParam String contenidoId) throws Exception {
        contenidoAcademicoService.guardarContenido(estudianteId, contenidoId);
        return ResponseEntity.ok(new ResponseDTO<>("Contenido guardado", null));
    }
}