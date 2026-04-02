package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.dto.CrearValoracionDTO;
import co.edu.uniquindio.red_academica.dto.InformacionContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.ContenidoAcademico;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import java.util.List;

public interface ContenidoAcademicoService {

    String crear(CrearContenidoAcademicoDTO dto) throws Exception;

    InformacionContenidoAcademicoDTO obtenerPorId(String id) throws Exception;

    List<InformacionContenidoAcademicoDTO> obtenerTodos();

    InformacionContenidoAcademicoDTO actualizar(String id, CrearContenidoAcademicoDTO dto) throws Exception;

    void eliminar(String id) throws Exception;

    List<InformacionContenidoAcademicoDTO> buscarPorTema(TEMA tema) throws Exception;

    List<InformacionContenidoAcademicoDTO> buscarPorAutor(String autorId) throws Exception;

    List<InformacionContenidoAcademicoDTO> buscarPorTipo(TipoContenido tipo) throws Exception;

    List<InformacionContenidoAcademicoDTO> buscarPorTitulo(String titulo) throws Exception;

    void agregarValoracion(CrearValoracionDTO dto) throws Exception;

    void guardarContenido(String estudianteId, String contenidoId) throws Exception;
}
