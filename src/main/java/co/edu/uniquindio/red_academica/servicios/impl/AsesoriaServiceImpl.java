package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearAsesoriaDTO;
import co.edu.uniquindio.red_academica.dto.GraficaEstadoAsesoriaDTO;
import co.edu.uniquindio.red_academica.dto.InformacionAsesoriaDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Asesoria;
import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import co.edu.uniquindio.red_academica.modelo.documentos.Mentor;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
import co.edu.uniquindio.red_academica.repositorios.AsesoriaRepository;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.repositorios.MentorRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.AsesoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AsesoriaServiceImpl implements AsesoriaService {

    private final AsesoriaRepository asesoriaRepository;
    private final MentorRepository mentorRepository;
    private final EstudianteRepository estudianteRepository;

    @Autowired
    public AsesoriaServiceImpl(AsesoriaRepository asesoriaRepository, MentorRepository mentorRepository, EstudianteRepository estudianteRepository) {
        this.asesoriaRepository = asesoriaRepository;
        this.mentorRepository = mentorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public String crear(CrearAsesoriaDTO dto) throws Exception {
        Asesoria asesoria = new Asesoria();
        asesoria.setId(java.util.UUID.randomUUID().toString());
        asesoria.setSolicitanteId(dto.solicitanteId());
        asesoria.setAsesorId(dto.asesorId());
        asesoria.setTema(dto.tema());
        asesoria.setFechaHora(dto.fechaHora());
        asesoria.setDescripcion(dto.descripcion());
        asesoria.setMedio(dto.medio());
        asesoria.setEstado(EstadoAsesoria.PENDIENTE);

        Asesoria guardada = asesoriaRepository.save(asesoria);
        return guardada.getId();
    }

    @Override
    public InformacionAsesoriaDTO obtenerPorId(String id) throws Exception {
        Asesoria asesoria = asesoriaRepository.findById(id)
                .orElseThrow(() -> new Exception("Asesoria no encontrada"));

        return new InformacionAsesoriaDTO(
                asesoria.getId(),
                asesoria.getSolicitanteId(),
                obtenerNombreSolicitante(asesoria.getSolicitanteId()),
                asesoria.getAsesorId(),
                obtenerNombreAsesor(asesoria.getAsesorId()),
                asesoria.getTema(),
                asesoria.getFechaHora(),
                asesoria.getDescripcion(),
                asesoria.getMedio(),
                asesoria.getEstado()
        );
    }

    @Override
    public List<InformacionAsesoriaDTO> obtenerTodos() {
        return asesoriaRepository.findAll().stream()
                .map(asesoria -> new InformacionAsesoriaDTO(
                        asesoria.getId(),
                        asesoria.getSolicitanteId(),
                        obtenerNombreSolicitante(asesoria.getSolicitanteId()),
                        asesoria.getAsesorId(),
                        obtenerNombreAsesor(asesoria.getAsesorId()),
                        asesoria.getTema(),
                        asesoria.getFechaHora(),
                        asesoria.getDescripcion(),
                        asesoria.getMedio(),
                        asesoria.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public InformacionAsesoriaDTO actualizar(String id, CrearAsesoriaDTO dto) throws Exception {
        Asesoria asesoria = asesoriaRepository.findById(id)
                .orElseThrow(() -> new Exception("Asesoria no encontrada"));

        asesoria.setSolicitanteId(dto.solicitanteId());
        asesoria.setAsesorId(dto.asesorId());
        asesoria.setTema(dto.tema());
        asesoria.setFechaHora(dto.fechaHora());
        asesoria.setDescripcion(dto.descripcion());
        asesoria.setMedio(dto.medio());

        Asesoria actualizada = asesoriaRepository.save(asesoria);
        return obtenerPorId(actualizada.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!asesoriaRepository.existsById(id)) {
            throw new Exception("Asesoria no encontrada");
        }
        asesoriaRepository.deleteById(id);
    }

    @Override
    public List<InformacionAsesoriaDTO> obtenerPorSolicitante(String solicitanteId) throws Exception {
        List<Asesoria> asesorias = asesoriaRepository.findBySolicitanteId(solicitanteId);
        return asesorias.stream()
                .map(asesoria -> new InformacionAsesoriaDTO(
                        asesoria.getId(),
                        asesoria.getSolicitanteId(),
                        obtenerNombreSolicitante(asesoria.getSolicitanteId()),
                        asesoria.getAsesorId(),
                        obtenerNombreAsesor(asesoria.getAsesorId()),
                        asesoria.getTema(),
                        asesoria.getFechaHora(),
                        asesoria.getDescripcion(),
                        asesoria.getMedio(),
                        asesoria.getEstado()
                ))
                .collect(Collectors.toList());
    }

    private String obtenerNombreAsesor(String asesorId) {
        return mentorRepository.findById(asesorId)
                .map(Mentor::getNombre)
                .orElse("Sin asignar");
    }

    private String obtenerNombreSolicitante(String solicitanteId) {
        return estudianteRepository.findById(solicitanteId)
                .map(Estudiante::getNombre)
                .orElse("Sin nombre");
    }

    @Override
    public List<InformacionAsesoriaDTO> obtenerPorAsesor(String asesorId) throws Exception {
        List<Asesoria> asesorias = asesoriaRepository.findByAsesorId(asesorId);
        return asesorias.stream()
                .map(asesoria -> new InformacionAsesoriaDTO(
                        asesoria.getId(),
                        asesoria.getSolicitanteId(),
                        obtenerNombreSolicitante(asesoria.getSolicitanteId()),
                        asesoria.getAsesorId(),
                        obtenerNombreAsesor(asesoria.getAsesorId()),
                        asesoria.getTema(),
                        asesoria.getFechaHora(),
                        asesoria.getDescripcion(),
                        asesoria.getMedio(),
                        asesoria.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionAsesoriaDTO> obtenerPorEstado(EstadoAsesoria estado) throws Exception {
        List<Asesoria> asesorias = asesoriaRepository.findByEstado(estado);
        return asesorias.stream()
                .map(asesoria -> new InformacionAsesoriaDTO(
                        asesoria.getId(),
                        asesoria.getSolicitanteId(),
                        obtenerNombreSolicitante(asesoria.getSolicitanteId()),
                        asesoria.getAsesorId(),
                        obtenerNombreAsesor(asesoria.getAsesorId()),
                        asesoria.getTema(),
                        asesoria.getFechaHora(),
                        asesoria.getDescripcion(),
                        asesoria.getMedio(),
                        asesoria.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void actualizarEstado(String id, EstadoAsesoria estado) throws Exception {
        Asesoria asesoria = asesoriaRepository.findById(id)
                .orElseThrow(() -> new Exception("Asesoria no encontrada"));

        asesoria.setEstado(estado);
        asesoriaRepository.save(asesoria);
    }

    @Override
    public List<GraficaEstadoAsesoriaDTO> obtenerGraficaEstadosPorAsesor(String asesorId) throws Exception {

        List<Asesoria> asesorias = asesoriaRepository.findByAsesorId(asesorId);

        Map<EstadoAsesoria, Long> conteo = asesorias.stream()
                .collect(Collectors.groupingBy(
                        Asesoria::getEstado,
                        Collectors.counting()
                ));

        return conteo.entrySet().stream()
                .map(entry -> new GraficaEstadoAsesoriaDTO(
                        entry.getKey(),
                        entry.getValue().intValue()
                ))
                .toList();
    }
}
