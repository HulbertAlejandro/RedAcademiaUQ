package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.EditarEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.InformacionEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.ItemEstudianteDTO;
import java.util.List;

public interface EstudianteServicio {
    
    String crearEstudiante(CrearEstudianteDTO estudianteDTO) throws Exception;
    
    void editarEstudiante(EditarEstudianteDTO estudianteDTO) throws Exception;
    
    void eliminarEstudiante(String idEstudiante) throws Exception;
    
    InformacionEstudianteDTO obtenerInformacionEstudiante(String idEstudiante) throws Exception;
    
    List<ItemEstudianteDTO> listarEstudiantes() throws Exception;
    
    InformacionEstudianteDTO buscarEstudiantePorEmail(String email) throws Exception;
    
    boolean existeEmail(String email) throws Exception;
    
    boolean existeCedula(String cedula) throws Exception;
}
