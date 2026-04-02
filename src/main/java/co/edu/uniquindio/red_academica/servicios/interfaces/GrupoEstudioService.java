package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.AbandonarGrupoDTO;
import co.edu.uniquindio.red_academica.dto.CrearGrupoEstudioDTO;
import co.edu.uniquindio.red_academica.dto.InformacionGrupoEstudioDTO;
import co.edu.uniquindio.red_academica.dto.RechazarGrupoDTO;
import co.edu.uniquindio.red_academica.dto.UnirseGrupoDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.GrupoEstudio;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import java.util.List;

public interface GrupoEstudioService {

    String crear(CrearGrupoEstudioDTO dto) throws Exception;

    InformacionGrupoEstudioDTO obtenerPorId(String id) throws Exception;

    List<InformacionGrupoEstudioDTO> obtenerTodos();

    InformacionGrupoEstudioDTO actualizar(String id, CrearGrupoEstudioDTO dto) throws Exception;

    void eliminar(String id) throws Exception;

    List<InformacionGrupoEstudioDTO> buscarPorTema(TEMA tema) throws Exception;

    List<InformacionGrupoEstudioDTO> buscarPorNombre(String nombre) throws Exception;

    void unirseGrupo(UnirseGrupoDTO dto) throws Exception;

    void abandonarGrupo(AbandonarGrupoDTO dto) throws Exception;

    void rechazarInvitacion(RechazarGrupoDTO dto) throws Exception;

    List<InformacionGrupoEstudioDTO> obtenerGruposDeEstudiante(String estudianteId) throws Exception;
}
