package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.AtenderSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.CerrarSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.CrearSolicitudAyudaDTO;
import co.edu.uniquindio.red_academica.dto.InformacionSolicitudAyudaDTO;
import co.edu.uniquindio.red_academica.dto.ResolverSolicitudDTO;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;

import java.util.List;

public interface SolicitudAyudaService {

    String crear(CrearSolicitudAyudaDTO dto) throws Exception;

    InformacionSolicitudAyudaDTO obtenerPorId(String id) throws Exception;

    List<InformacionSolicitudAyudaDTO> obtenerTodos();

    InformacionSolicitudAyudaDTO actualizar(String id, CrearSolicitudAyudaDTO dto) throws Exception;

    void eliminar(String id) throws Exception;

    List<InformacionSolicitudAyudaDTO> obtenerPorSolicitante(String solicitanteId) throws Exception;

    List<InformacionSolicitudAyudaDTO> obtenerPorTema(TEMA tema) throws Exception;

    List<InformacionSolicitudAyudaDTO> obtenerPorEstado(EstadoSolicitud estado) throws Exception;

    List<InformacionSolicitudAyudaDTO> obtenerPorUrgencia() throws Exception;

    List<InformacionSolicitudAyudaDTO> obtenerActivas() throws Exception;

    void atenderSolicitud(AtenderSolicitudDTO dto) throws Exception;

    void resolverSolicitud(ResolverSolicitudDTO dto) throws Exception;

    void cerrarSolicitud(CerrarSolicitudDTO dto) throws Exception;
}