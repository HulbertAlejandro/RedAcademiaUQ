package co.edu.uniquindio.red_academica.controllers;

import co.edu.uniquindio.red_academica.dto.CrearRespuestaSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.InformacionRespuestaSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.MensajeDTO;
import co.edu.uniquindio.red_academica.dto.ResponseDTO;
import co.edu.uniquindio.red_academica.servicios.interfaces.RespuestaSolicitudService;
import com.mongodb.client.gridfs.model.GridFSFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas-solicitud")
@RequiredArgsConstructor
public class RespuestaSolicitudController {

    private final RespuestaSolicitudService respuestaSolicitudService;
    private final GridFsTemplate gridFsTemplate;

    @PostMapping("/crear")
    public ResponseEntity<ResponseDTO<String>> crearRespuesta(@Valid @RequestBody CrearRespuestaSolicitudDTO dto) throws Exception {
        String id = respuestaSolicitudService.crearRespuesta(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Respuesta creada correctamente", id));
    }

    @PostMapping(value = "/subir-adjunto", consumes = "multipart/form-data")
    public ResponseEntity<MensajeDTO<String>> subirAdjunto(
            @RequestParam("respuestaId") String respuestaId,
            @RequestParam("archivo") MultipartFile archivo
    ) throws Exception {
        respuestaSolicitudService.subirAdjunto(respuestaId, archivo);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Adjunto subido correctamente"));
    }

    @GetMapping("/solicitud/{solicitudId}")
    public ResponseEntity<ResponseDTO<List<InformacionRespuestaSolicitudDTO>>> obtenerPorSolicitud(@PathVariable String solicitudId) throws Exception {
        return ResponseEntity.ok(
                new ResponseDTO<>("Respuestas de la solicitud", respuestaSolicitudService.obtenerPorSolicitud(solicitudId))
        );
    }

    @GetMapping("/{respuestaId}")
    public ResponseEntity<ResponseDTO<InformacionRespuestaSolicitudDTO>> obtenerPorId(@PathVariable String respuestaId) throws Exception {
        return ResponseEntity.ok(
                new ResponseDTO<>("Respuesta encontrada", respuestaSolicitudService.obtenerPorId(respuestaId))
        );
    }

    @PutMapping("/marcar-final/{respuestaId}")
    public ResponseEntity<ResponseDTO<String>> marcarComoFinal(@PathVariable String respuestaId) throws Exception {
        respuestaSolicitudService.marcarRespuestaFinal(respuestaId);
        return ResponseEntity.ok(new ResponseDTO<>("Respuesta marcada como final", null));
    }

    @GetMapping("/archivo/{archivoId}")
    public ResponseEntity<Resource> obtenerAdjunto(@PathVariable String archivoId) throws Exception {
        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(archivoId)))
        );

        if (file == null) {
            throw new Exception("Archivo adjunto no encontrado");
        }

        GridFsResource resource = gridFsTemplate.getResource(file);

        String contentType = "application/octet-stream";
        if (file.getMetadata() != null) {
            if (file.getMetadata().get("_contentType") != null) {
                contentType = file.getMetadata().getString("_contentType");
            } else if (file.getMetadata().get("contentType") != null) {
                contentType = file.getMetadata().getString("contentType");
            }
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}