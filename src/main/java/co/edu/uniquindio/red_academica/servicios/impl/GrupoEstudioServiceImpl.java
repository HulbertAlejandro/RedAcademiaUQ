package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.AbandonarGrupoDTO;
import co.edu.uniquindio.red_academica.dto.CrearGrupoEstudioDTO;
import co.edu.uniquindio.red_academica.dto.InformacionGrupoEstudioDTO;
import co.edu.uniquindio.red_academica.dto.InformacionParticipanteDTO;
import co.edu.uniquindio.red_academica.dto.RechazarGrupoDTO;
import co.edu.uniquindio.red_academica.dto.UnirseGrupoDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.GrupoEstudio;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.repositorios.GrupoEstudioRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.GrupoEstudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoEstudioServiceImpl implements GrupoEstudioService {

    private final GrupoEstudioRepository grupoRepository;
    private final EstudianteRepository estudianteRepository;

    @Autowired
    public GrupoEstudioServiceImpl(GrupoEstudioRepository grupoRepository,
                                   EstudianteRepository estudianteRepository) {
        this.grupoRepository = grupoRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public String crear(CrearGrupoEstudioDTO dto) throws Exception {
        GrupoEstudio grupo = new GrupoEstudio();
        grupo.setId(java.util.UUID.randomUUID().toString());
        grupo.setNombre(dto.nombre());
        grupo.setTema(dto.tema());
        grupo.setDescripcion(dto.descripcion());
        grupo.setParticipantes(new ArrayList<>());
        grupo.setFechaCreacion(LocalDateTime.now());

        GrupoEstudio guardado = grupoRepository.save(grupo);
        return guardado.getId();
    }

    @Override
    public InformacionGrupoEstudioDTO obtenerPorId(String id) throws Exception {
        GrupoEstudio grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new Exception("Grupo no encontrado"));

        List<String> participantes = grupo.getParticipantes() != null ? grupo.getParticipantes() : new ArrayList<>();

        List<InformacionParticipanteDTO> participantesDTO = participantes.stream()
                .map(this::convertirParticipante)
                .collect(Collectors.toList());

        return new InformacionGrupoEstudioDTO(
                grupo.getId(),
                grupo.getNombre(),
                grupo.getTema(),
                grupo.getDescripcion(),
                participantesDTO,
                participantes.size(),
                grupo.getFechaCreacion(),
                false
        );
    }

    @Override
    public List<InformacionGrupoEstudioDTO> obtenerTodos() {
        return grupoRepository.findAll().stream()
                .map(grupo -> {
                    List<String> participantes = grupo.getParticipantes() != null ? grupo.getParticipantes() : new ArrayList<>();

                    List<InformacionParticipanteDTO> participantesDTO = participantes.stream()
                            .map(this::convertirParticipante)
                            .collect(Collectors.toList());

                    return new InformacionGrupoEstudioDTO(
                            grupo.getId(),
                            grupo.getNombre(),
                            grupo.getTema(),
                            grupo.getDescripcion(),
                            participantesDTO,
                            participantes.size(),
                            grupo.getFechaCreacion(),
                            false
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public InformacionGrupoEstudioDTO actualizar(String id, CrearGrupoEstudioDTO dto) throws Exception {
        GrupoEstudio grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new Exception("Grupo no encontrado"));

        grupo.setNombre(dto.nombre());
        grupo.setTema(dto.tema());
        grupo.setDescripcion(dto.descripcion());

        GrupoEstudio actualizado = grupoRepository.save(grupo);
        return obtenerPorId(actualizado.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!grupoRepository.existsById(id)) {
            throw new Exception("Grupo no encontrado");
        }
        grupoRepository.deleteById(id);
    }

    @Override
    public List<InformacionGrupoEstudioDTO> buscarPorTema(co.edu.uniquindio.red_academica.modelo.enums.TEMA tema) throws Exception {
        List<GrupoEstudio> grupos = grupoRepository.findByTema(tema);
        return grupos.stream()
                .map(grupo -> {
                    List<String> participantes = grupo.getParticipantes() != null ? grupo.getParticipantes() : new ArrayList<>();

                    List<InformacionParticipanteDTO> participantesDTO = participantes.stream()
                            .map(this::convertirParticipante)
                            .collect(Collectors.toList());

                    return new InformacionGrupoEstudioDTO(
                            grupo.getId(),
                            grupo.getNombre(),
                            grupo.getTema(),
                            grupo.getDescripcion(),
                            participantesDTO,
                            participantes.size(),
                            grupo.getFechaCreacion(),
                            false
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionGrupoEstudioDTO> buscarPorNombre(String nombre) throws Exception {
        List<GrupoEstudio> grupos = grupoRepository.findByNombreContainingIgnoreCase(nombre);
        return grupos.stream()
                .map(grupo -> {
                    List<String> participantes = grupo.getParticipantes() != null ? grupo.getParticipantes() : new ArrayList<>();

                    List<InformacionParticipanteDTO> participantesDTO = participantes.stream()
                            .map(this::convertirParticipante)
                            .collect(Collectors.toList());

                    return new InformacionGrupoEstudioDTO(
                            grupo.getId(),
                            grupo.getNombre(),
                            grupo.getTema(),
                            grupo.getDescripcion(),
                            participantesDTO,
                            participantes.size(),
                            grupo.getFechaCreacion(),
                            false
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void unirseGrupo(UnirseGrupoDTO dto) throws Exception {
        GrupoEstudio grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new Exception("Grupo no encontrado"));

        if (!estudianteRepository.existsById(dto.estudianteId())) {
            throw new Exception("Estudiante no encontrado");
        }

        List<String> participantes = grupo.getParticipantes();
        if (participantes == null) {
            participantes = new ArrayList<>();
        } else {
            participantes = new ArrayList<>(participantes);
        }

        if (participantes.contains(dto.estudianteId())) {
            throw new Exception("El estudiante ya es miembro del grupo");
        }

        participantes.add(dto.estudianteId());
        grupo.setParticipantes(participantes);
        grupoRepository.save(grupo);

        estudianteRepository.findById(dto.estudianteId()).ifPresent(estudiante -> {
            List<String> gruposEstudiante = estudiante.getGruposEstudio();
            if (gruposEstudiante == null) {
                gruposEstudiante = new ArrayList<>();
            } else {
                gruposEstudiante = new ArrayList<>(gruposEstudiante);
            }

            if (!gruposEstudiante.contains(dto.grupoId())) {
                gruposEstudiante.add(dto.grupoId());
                estudiante.setGruposEstudio(gruposEstudiante);
                estudianteRepository.save(estudiante);
            }
        });
    }

    @Override
    public void abandonarGrupo(AbandonarGrupoDTO dto) throws Exception {
        GrupoEstudio grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new Exception("Grupo no encontrado"));

        List<String> participantes = grupo.getParticipantes();
        if (participantes == null || !participantes.contains(dto.estudianteId())) {
            throw new Exception("El estudiante no es miembro del grupo");
        }

        participantes = new ArrayList<>(participantes);
        participantes.remove(dto.estudianteId());
        grupo.setParticipantes(participantes);
        grupoRepository.save(grupo);

        estudianteRepository.findById(dto.estudianteId()).ifPresent(estudiante -> {
            List<String> gruposEstudiante = estudiante.getGruposEstudio();
            if (gruposEstudiante != null) {
                gruposEstudiante = new ArrayList<>(gruposEstudiante);
                gruposEstudiante.remove(dto.grupoId());
                estudiante.setGruposEstudio(gruposEstudiante);
                estudianteRepository.save(estudiante);
            }
        });
    }

    @Override
    public void rechazarInvitacion(RechazarGrupoDTO dto) throws Exception {
        var estudiante = estudianteRepository.findById(dto.estudianteId())
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        List<String> gruposRechazados = estudiante.getGruposRechazados();
        if (gruposRechazados == null) {
            gruposRechazados = new ArrayList<>();
        } else {
            gruposRechazados = new ArrayList<>(gruposRechazados);
        }

        if (gruposRechazados.contains(dto.grupoId())) {
            throw new Exception("El grupo ya fue rechazado");
        }

        gruposRechazados.add(dto.grupoId());
        estudiante.setGruposRechazados(gruposRechazados);
        estudianteRepository.save(estudiante);
    }

    @Override
    public List<InformacionGrupoEstudioDTO> obtenerGruposDeEstudiante(String estudianteId) throws Exception {
        List<GrupoEstudio> grupos = grupoRepository.findByParticipantesContaining(estudianteId);
        return grupos.stream()
                .map(grupo -> {
                    List<String> participantes = grupo.getParticipantes() != null ? grupo.getParticipantes() : new ArrayList<>();

                    List<InformacionParticipanteDTO> participantesDTO = participantes.stream()
                            .map(this::convertirParticipante)
                            .collect(Collectors.toList());

                    return new InformacionGrupoEstudioDTO(
                            grupo.getId(),
                            grupo.getNombre(),
                            grupo.getTema(),
                            grupo.getDescripcion(),
                            participantesDTO,
                            participantes.size(),
                            grupo.getFechaCreacion(),
                            participantes.contains(estudianteId)
                    );
                })
                .collect(Collectors.toList());
    }

    private InformacionParticipanteDTO convertirParticipante(String estudianteId) {
        return estudianteRepository.findById(estudianteId)
                .map(estudiante -> new InformacionParticipanteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        LocalDateTime.now()
                ))
                .orElse(new InformacionParticipanteDTO(
                        estudianteId,
                        "Estudiante no encontrado",
                        "",
                        LocalDateTime.now()
                ));
    }
}