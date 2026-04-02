package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.AtenderSolicitudDTO;
import co.edu.uniquindio.red_academica.dto.CrearSolicitudAyudaDTO;
import co.edu.uniquindio.red_academica.dto.InformacionSolicitudAyudaDTO;
import co.edu.uniquindio.red_academica.dto.ResolverSolicitudDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.SolicitudAyuda;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
import co.edu.uniquindio.red_academica.repositorios.SolicitudAyudaRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.SolicitudAyudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudAyudaServiceImpl implements SolicitudAyudaService {

    private final SolicitudAyudaRepository solicitudRepository;

    @Autowired
    public SolicitudAyudaServiceImpl(SolicitudAyudaRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public String crear(CrearSolicitudAyudaDTO dto) throws Exception {
        SolicitudAyuda solicitud = new SolicitudAyuda();
        solicitud.setId(java.util.UUID.randomUUID().toString());
        solicitud.setTema(dto.tema());
        solicitud.setUrgencia(dto.urgencia());
        solicitud.setSolicitanteId(dto.solicitanteId());
        solicitud.setDescripcion(dto.descripcion());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaCreacion(LocalDateTime.now());

        SolicitudAyuda guardada = solicitudRepository.save(solicitud);
        return guardada.getId();
    }

    @Override
    public InformacionSolicitudAyudaDTO obtenerPorId(String id) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        return new InformacionSolicitudAyudaDTO(
                solicitud.getId(),
                solicitud.getTema(),
                solicitud.getUrgencia(),
                solicitud.getSolicitanteId(),
                "",
                solicitud.getDescripcion(),
                solicitud.getEstado(),
                solicitud.getFechaCreacion(),
                solicitud.getIdContenidoResuelto(),
                ""
        );
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerTodos() {
        return solicitudRepository.findAll().stream()
                .map(solicitud -> new InformacionSolicitudAyudaDTO(
                        solicitud.getId(),
                        solicitud.getTema(),
                        solicitud.getUrgencia(),
                        solicitud.getSolicitanteId(),
                        "",
                        solicitud.getDescripcion(),
                        solicitud.getEstado(),
                        solicitud.getFechaCreacion(),
                        solicitud.getIdContenidoResuelto(),
                        ""
                ))
                .collect(Collectors.toList());
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
        List<SolicitudAyuda> solicitudes = solicitudRepository.findBySolicitanteId(solicitanteId);
        return solicitudes.stream()
                .map(solicitud -> new InformacionSolicitudAyudaDTO(
                        solicitud.getId(),
                        solicitud.getTema(),
                        solicitud.getUrgencia(),
                        solicitud.getSolicitanteId(),
                        "",
                        solicitud.getDescripcion(),
                        solicitud.getEstado(),
                        solicitud.getFechaCreacion(),
                        solicitud.getIdContenidoResuelto(),
                        ""
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorTema(co.edu.uniquindio.red_academica.modelo.enums.TEMA tema) throws Exception {
        List<SolicitudAyuda> solicitudes = solicitudRepository.findByTema(tema);
        return solicitudes.stream()
                .map(solicitud -> new InformacionSolicitudAyudaDTO(
                        solicitud.getId(),
                        solicitud.getTema(),
                        solicitud.getUrgencia(),
                        solicitud.getSolicitanteId(),
                        "",
                        solicitud.getDescripcion(),
                        solicitud.getEstado(),
                        solicitud.getFechaCreacion(),
                        solicitud.getIdContenidoResuelto(),
                        ""
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorEstado(EstadoSolicitud estado) throws Exception {
        List<SolicitudAyuda> solicitudes = solicitudRepository.findByEstado(estado);
        return solicitudes.stream()
                .map(solicitud -> new InformacionSolicitudAyudaDTO(
                        solicitud.getId(),
                        solicitud.getTema(),
                        solicitud.getUrgencia(),
                        solicitud.getSolicitanteId(),
                        "",
                        solicitud.getDescripcion(),
                        solicitud.getEstado(),
                        solicitud.getFechaCreacion(),
                        solicitud.getIdContenidoResuelto(),
                        ""
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionSolicitudAyudaDTO> obtenerPorUrgencia() throws Exception {
        List<SolicitudAyuda> solicitudes = solicitudRepository.findByUrgenciaGreaterThanEqualOrderByUrgenciaDesc(1);
        return solicitudes.stream()
                .map(solicitud -> new InformacionSolicitudAyudaDTO(
                        solicitud.getId(),
                        solicitud.getTema(),
                        solicitud.getUrgencia(),
                        solicitud.getSolicitanteId(),
                        "",
                        solicitud.getDescripcion(),
                        solicitud.getEstado(),
                        solicitud.getFechaCreacion(),
                        solicitud.getIdContenidoResuelto(),
                        ""
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void atenderSolicitud(AtenderSolicitudDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(dto.solicitudId())
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new Exception("La solicitud ya está siendo atendida o fue resuelta");
        }

        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitudRepository.save(solicitud);
    }

    @Override
    public void resolverSolicitud(ResolverSolicitudDTO dto) throws Exception {
        SolicitudAyuda solicitud = solicitudRepository.findById(dto.solicitudId())
                .orElseThrow(() -> new Exception("Solicitud no encontrada"));

        if (solicitud.getEstado() != EstadoSolicitud.EN_PROCESO) {
            throw new Exception("La solicitud debe estar en proceso para ser resuelta");
        }

        solicitud.setEstado(EstadoSolicitud.RESUELTA);
        solicitud.setIdContenidoResuelto(dto.contenidoId());
        solicitudRepository.save(solicitud);
    }
}
