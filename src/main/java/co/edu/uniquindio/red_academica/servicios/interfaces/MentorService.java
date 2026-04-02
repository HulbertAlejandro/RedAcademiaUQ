package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearMentorDTO;
import co.edu.uniquindio.red_academica.dto.InformacionMentorDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Mentor;
import java.util.List;
import java.util.Optional;

public interface MentorService {

    String crear(CrearMentorDTO dto) throws Exception;

    InformacionMentorDTO obtenerPorId(String id) throws Exception;

    List<InformacionMentorDTO> obtenerTodos();

    InformacionMentorDTO actualizar(String id, CrearMentorDTO dto) throws Exception;

    void eliminar(String id) throws Exception;

    List<InformacionMentorDTO> buscarPorEspecialidad(String especialidad) throws Exception;

    List<InformacionMentorDTO> buscarPorNombre(String nombre) throws Exception;

    boolean existePorCorreo(String correo);
}
