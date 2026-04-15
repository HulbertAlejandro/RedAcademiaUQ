package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion;
import jakarta.validation.Valid;

import java.util.List;

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

    void iniciarRecuperacionPassword(String email) throws Exception;

    void verificarCodigoRecuperacion(String email, String codigo) throws Exception;

    void restablecerPassword(String email, String codigo, String passwordNueva) throws Exception;

    TokenDTO autenticar(LoginDTO dto) throws Exception;

    void cambiarContrasena(String estudianteId, CambiarPasswordDTO dto) throws Exception;

    String obtenerPorCorreo(@Valid ObtenerNombreDTO usuario);
}
