package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.BuscarContenidoDTO;
import co.edu.uniquindio.red_academica.dto.CrearContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.ContenidoAcademico;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContenidoAcademicoService {

    String subirContenido(CrearContenidoAcademicoDTO dto, MultipartFile archivo) throws Exception;

    ContenidoAcademico obtenerContenidoPorId(String id) throws Exception;

    List<ContenidoAcademico> obtenerTodosContenidos();

    byte[] obtenerArchivoContenido(String id) throws Exception;

    List<ContenidoAcademico> buscarContenidos(BuscarContenidoDTO dto);
}