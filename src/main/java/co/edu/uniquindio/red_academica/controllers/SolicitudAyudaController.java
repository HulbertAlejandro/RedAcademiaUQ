package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.servicios.interfaces.SolicitudAyudaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solicitudes-ayuda")
public class SolicitudAyudaController {

    private final SolicitudAyudaService solicitudAyudaService;

    @PostMapping("/crear-solicitud")
    public ResponseEntity<ResponseDTO<String>> crear(@Valid @RequestBody CrearSolicitudAyudaDTO dto) throws Exception {
        String id = solicitudAyudaService.crear(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud de ayuda creada exitosamente", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionSolicitudAyudaDTO>> obtenerPorId(@PathVariable String id) throws Exception {
        InformacionSolicitudAyudaDTO solicitud = solicitudAyudaService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud encontrada", solicitud));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<InformacionSolicitudAyudaDTO>>> obtenerTodos() {
        List<InformacionSolicitudAyudaDTO> solicitudes = solicitudAyudaService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDTO<>("Lista de solicitudes", solicitudes));
    }

    @GetMapping("/activas")
    public ResponseEntity<ResponseDTO<List<InformacionSolicitudAyudaDTO>>> obtenerActivas() throws Exception {
        return ResponseEntity.ok(new ResponseDTO<>("Solicitudes activas", solicitudAyudaService.obtenerActivas()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<InformacionSolicitudAyudaDTO>> actualizar(@PathVariable String id, @RequestBody CrearSolicitudAyudaDTO dto) throws Exception {
        InformacionSolicitudAyudaDTO solicitud = solicitudAyudaService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud actualizada", solicitud));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminar(@PathVariable String id) throws Exception {
        solicitudAyudaService.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud eliminada", null));
    }

    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<ResponseDTO<List<InformacionSolicitudAyudaDTO>>> obtenerPorSolicitante(@PathVariable String solicitanteId) throws Exception {
        List<InformacionSolicitudAyudaDTO> solicitudes = solicitudAyudaService.obtenerPorSolicitante(solicitanteId);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitudes del solicitante", solicitudes));
    }

    @GetMapping("/tema/{tema}")
    public ResponseEntity<ResponseDTO<List<InformacionSolicitudAyudaDTO>>> obtenerPorTema(@PathVariable String tema) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.TEMA temaEnum = co.edu.uniquindio.red_academica.modelo.enums.TEMA.valueOf(tema.toUpperCase());
        List<InformacionSolicitudAyudaDTO> solicitudes = solicitudAyudaService.obtenerPorTema(temaEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitudes por tema", solicitudes));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ResponseDTO<List<InformacionSolicitudAyudaDTO>>> obtenerPorEstado(@PathVariable String estado) throws Exception {
        co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud estadoEnum = co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud.valueOf(estado.toUpperCase());
        List<InformacionSolicitudAyudaDTO> solicitudes = solicitudAyudaService.obtenerPorEstado(estadoEnum);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitudes por estado", solicitudes));
    }

    @GetMapping("/urgentes")
    public ResponseEntity<ResponseDTO<List<InformacionSolicitudAyudaDTO>>> obtenerPorUrgencia() throws Exception {
        List<InformacionSolicitudAyudaDTO> solicitudes = solicitudAyudaService.obtenerPorUrgencia();
        return ResponseEntity.ok(new ResponseDTO<>("Solicitudes urgentes", solicitudes));
    }

    @PostMapping("/atender")
    public ResponseEntity<ResponseDTO<String>> atenderSolicitud(@RequestBody AtenderSolicitudDTO dto) throws Exception {
        solicitudAyudaService.atenderSolicitud(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud atendida", null));
    }

    @PostMapping("/resolver")
    public ResponseEntity<ResponseDTO<String>> resolverSolicitud(@RequestBody ResolverSolicitudDTO dto) throws Exception {
        solicitudAyudaService.resolverSolicitud(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud actualizada con contenido", null));
    }

    @PostMapping("/cerrar")
    public ResponseEntity<ResponseDTO<String>> cerrarSolicitud(@Valid @RequestBody CerrarSolicitudDTO dto) throws Exception {
        solicitudAyudaService.cerrarSolicitud(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Solicitud cerrada correctamente", null));
    }
}