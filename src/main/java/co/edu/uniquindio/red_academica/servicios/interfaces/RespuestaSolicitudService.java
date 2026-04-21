package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.InformacionRespuestaSolicitudDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RespuestaSolicitudService {

    String crearRespuesta(co.edu.uniquindio.red_academica.dto.CrearRespuestaSolicitudDTO dto) throws Exception;

    void subirAdjunto(String respuestaId, MultipartFile archivo) throws Exception;

    List<InformacionRespuestaSolicitudDTO> obtenerPorSolicitud(String solicitudId) throws Exception;

    InformacionRespuestaSolicitudDTO obtenerPorId(String respuestaId) throws Exception;

    void marcarRespuestaFinal(String respuestaId) throws Exception;
}