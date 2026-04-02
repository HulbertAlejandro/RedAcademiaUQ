package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.InformacionEstudianteDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion;
import java.util.List;
import java.util.Optional;

public interface EstudianteService {

    String crear(CrearEstudianteDTO dto) throws Exception;

    InformacionEstudianteDTO obtenerPorId(String id) throws Exception;

    List<InformacionEstudianteDTO> obtenerTodos();

    InformacionEstudianteDTO actualizar(String id, CrearEstudianteDTO dto) throws Exception;

    void eliminar(String id) throws Exception;

    void agregarAmigo(String estudianteId, String amigoId) throws Exception;

    void eliminarAmigo(String estudianteId, String amigoId) throws Exception;

    List<InformacionEstudianteDTO> buscarPorNombre(String nombre) throws Exception;

    List<InformacionEstudianteDTO> obtenerPorNivel(NivelParticipacion nivel) throws Exception;

    void agregarPuntosParticipacion(String estudianteId, int puntos) throws Exception;

    boolean existePorCorreo(String correo);
}
