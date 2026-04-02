package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearAsesoriaDTO;
import co.edu.uniquindio.red_academica.dto.InformacionAsesoriaDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Asesoria;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
import java.util.List;

public interface AsesoriaService {

    String crear(CrearAsesoriaDTO dto) throws Exception;

    InformacionAsesoriaDTO obtenerPorId(String id) throws Exception;

    List<InformacionAsesoriaDTO> obtenerTodos();

    InformacionAsesoriaDTO actualizar(String id, CrearAsesoriaDTO dto) throws Exception;

    void eliminar(String id) throws Exception;

    List<InformacionAsesoriaDTO> obtenerPorSolicitante(String solicitanteId) throws Exception;

    List<InformacionAsesoriaDTO> obtenerPorAsesor(String asesorId) throws Exception;

    List<InformacionAsesoriaDTO> obtenerPorEstado(EstadoAsesoria estado) throws Exception;

    void actualizarEstado(String id, EstadoAsesoria estado) throws Exception;
}
