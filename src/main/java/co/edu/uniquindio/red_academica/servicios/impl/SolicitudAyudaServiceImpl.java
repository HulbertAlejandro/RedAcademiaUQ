package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.AtenderSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.CerrarSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.CrearSolicitudAyudaDTO;
import co.edu.uniquindio.red_academica.dto.InformacionSolicitudAyudaDTO;
import co.edu.uniquindio.red_academica.dto.ResolverSolicitudDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import co.edu.uniquindio.red_academica.modelo.documentos.SolicitudAyuda;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.repositorios.SolicitudAyudaRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.SolicitudAyudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudAyudaServiceImpl implements SolicitudAyudaService {

    private final SolicitudAyudaRepository solicitudRepository;
    private final EstudianteRepository estudianteRepository;

    @Autowired
    public SolicitudAyudaServiceImpl(
            SolicitudAyudaRepository solicitudRepository,
            EstudianteRepository estudianteRepository
    ) {
        this.solicitudRepository = solicitudRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public String crear(CrearSolicitudAyudaDTO dto) throws Exception {
        SolicitudAyuda solicitud = new SolicitudAyuda();
        solicitud.setId(java.util.UUID.randomUUID().toString());
        solicitud.setTema(dto.tema());
        solicitud.setUrgencia(dto.urgencia());
        solicitud.setSolicitanteId(dto.solicitanteId());
        solicitud.setDescripcion(dto.descripcion());
        solicitud.setEstado(EstadoSolicitud.ABIERTA);
        solicitud.setFechaCreacion(LocalDateTime.now());

        SolicitudAyuda guardada = solicitudRepository.save(solicitud);
        return guardada.getId();
    }

    @Override
    public InformacionSolicitudAyudaDTO obtenerPorId(String id) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        return mapToDTO(solicitud);
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerTodos() {
        return solicitudRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public InformacionSolicitudAyudaDTO actualizar(String id, CrearSolicitudAyudaDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        solicitud.setTema(dto.tema());
        solicitud.setUrgencia(dto.urgencia());
        solicitud.setSolicitanteId(dto.solicitanteId());
        solicitud.setDescripcion(dto.descripcion());

        SolicitudAyuda actualizada = solicitudRepository.save(solicitud);
        return obtenerPorId(actualizada.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!solicitudRepository.existsById(id)) {
            throw new Exception("Solicitud no encontrada");
        }
        solicitudRepository.deleteById(id);
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorSolicitante(String solicitanteId) throws Exception {
        return solicitudRepository.findBySolicitanteId(solicitanteId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorTema(co.edu.uniquindio.red_academica.modelo.enums.TEMA tema) throws Exception {
        return solicitudRepository.findByTema(tema).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorEstado(EstadoSolicitud estado) throws Exception {
        return solicitudRepository.findByEstado(estado).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorUrgencia() throws Exception {
        return solicitudRepository.findByUrgenciaGreaterThanEqualOrderByUrgenciaDesc(1).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerActivas() throws Exception {
        List<SolicitudAyuda> abiertas = solicitudRepository.findByEstado(EstadoSolicitud.ABIERTA);
        List<SolicitudAyuda> enProceso = solicitudRepository.findByEstado(EstadoSolicitud.EN_PROCESO);

        List<SolicitudAyuda> todas = new ArrayList<>();
        todas.addAll(abiertas);
        todas.addAll(enProceso);

        return todas.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void atenderSolicitud(AtenderSolicitudDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(dto.solicitudId())
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        if (solicitud.getEstado() == EstadoSolicitud.CERRADA) {
            throw new Exception("La solicitud ya fue cerrada");
        }

        if (solicitud.getEstado() == EstadoSolicitud.ABIERTA) {
            solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
            solicitudRepository.save(solicitud);
        }
    }

    @Override
    public void resolverSolicitud(ResolverSolicitudDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(dto.solicitudId())
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        if (solicitud.getEstado() == EstadoSolicitud.CERRADA) {
            throw new Exception("La solicitud ya está cerrada");
        }

        solicitud.setIdContenidoResuelto(dto.contenidoId());
        solicitudRepository.save(solicitud);
    }

    @Override
    public void cerrarSolicitud(CerrarSolicitudDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(dto.solicitudId())
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        if (!solicitud.getSolicitanteId().equals(dto.solicitanteId())) {
            throw new Exception("No tienes permiso para cerrar esta solicitud");
        }

        solicitud.setEstado(EstadoSolicitud.CERRADA);
        solicitudRepository.save(solicitud);
    }

    private InformacionSolicitudAyudaDTO mapToDTO(SolicitudAyuda solicitud) {
        return new InformacionSolicitudAyudaDTO(
                solicitud.getId(),
                solicitud.getTema(),
                solicitud.getUrgencia(),
                solicitud.getSolicitanteId(),
                obtenerNombreSolicitante(solicitud.getSolicitanteId()),
                solicitud.getDescripcion(),
                solicitud.getEstado(),
                solicitud.getFechaCreacion(),
                solicitud.getIdContenidoResuelto(),
                ""
        );
    }

    private String obtenerNombreSolicitante(String solicitanteId) {
        return estudianteRepository.findById(solicitanteId)
                .map(Estudiante::getNombre)
                .orElse("Usuario no encontrado");
    }
}