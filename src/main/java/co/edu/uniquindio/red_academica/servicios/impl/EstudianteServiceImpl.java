package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.InformacionEstudianteDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public String crear(CrearEstudianteDTO dto) throws Exception {
        if (existePorCorreo(dto.email())) {
            throw new Exception("El correo ya está registrado");
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setId(java.util.UUID.randomUUID().toString());
        estudiante.setNombre(dto.nombre());
        estudiante.setCorreo(dto.email());
        estudiante.setContrasena(dto.password());
        estudiante.setPuntosParticipacion(0);
        estudiante.setNivel(NivelParticipacion.determinarNivel(0));
        estudiante.setContenidosSubidos(List.of());
        estudiante.setAmigos(List.of());
        estudiante.setGruposEstudio(List.of());
        estudiante.setGruposRechazados(List.of());

        Estudiante guardado = estudianteRepository.save(estudiante);
        return guardado.getId();
    }

    @Override
    public InformacionEstudianteDTO obtenerPorId(String id) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        return new InformacionEstudianteDTO(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getCorreo(),
                estudiante.getNivel().toString(),
                estudiante.getPuntosParticipacion(),
                estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
        );
    }

    @Override
    public List<InformacionEstudianteDTO> obtenerTodos() {
        return estudianteRepository.findAll().stream()
                .map(estudiante -> new InformacionEstudianteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        estudiante.getNivel().toString(),
                        estudiante.getPuntosParticipacion(),
                        estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                        estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                        estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public InformacionEstudianteDTO actualizar(String id, CrearEstudianteDTO dto) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        if (!estudiante.getCorreo().equals(dto.email()) && existePorCorreo(dto.email())) {
            throw new Exception("El correo ya está registrado");
        }

        estudiante.setNombre(dto.nombre());
        estudiante.setCorreo(dto.email());
        estudiante.setContrasena(dto.password());

        Estudiante actualizado = estudianteRepository.save(estudiante);
        return obtenerPorId(actualizado.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!estudianteRepository.existsById(id)) {
            throw new Exception("Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }

    @Override
    public void agregarAmigo(String estudianteId, String amigoId) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        Estudiante amigo = estudianteRepository.findById(amigoId)
                .orElseThrow(() -> new Exception("Amigo no encontrado"));

        if (estudianteId.equals(amigoId)) {
            throw new Exception("No puedes agregarte a ti mismo como amigo");
        }

        List<String> amigos = estudiante.getAmigos();
        if (amigos == null) {
            amigos = List.of();
        }

        if (amigos.contains(amigoId)) {
            throw new Exception("El usuario ya es tu amigo");
        }

        amigos.add(amigoId);
        estudiante.setAmigos(amigos);
        estudianteRepository.save(estudiante);
    }

    @Override
    public void eliminarAmigo(String estudianteId, String amigoId) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        List<String> amigos = estudiante.getAmigos();
        if (amigos == null || !amigos.contains(amigoId)) {
            throw new Exception("El usuario no está en tu lista de amigos");
        }

        amigos.remove(amigoId);
        estudiante.setAmigos(amigos);
        estudianteRepository.save(estudiante);
    }

    @Override
    public List<InformacionEstudianteDTO> buscarPorNombre(String nombre) throws Exception {
        List<Estudiante> estudiantes = estudianteRepository.findByNombreContainingIgnoreCase(nombre);
        return estudiantes.stream()
                .map(estudiante -> new InformacionEstudianteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        estudiante.getNivel().toString(),
                        estudiante.getPuntosParticipacion(),
                        estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                        estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                        estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionEstudianteDTO> obtenerPorNivel(NivelParticipacion nivel) throws Exception {
        List<Estudiante> estudiantes = estudianteRepository.findByNivel(nivel);
        return estudiantes.stream()
                .map(estudiante -> new InformacionEstudianteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        estudiante.getNivel().toString(),
                        estudiante.getPuntosParticipacion(),
                        estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                        estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                        estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void agregarPuntosParticipacion(String estudianteId, int puntos) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        int nuevosPuntos = estudiante.getPuntosParticipacion() + puntos;
        estudiante.setPuntosParticipacion(nuevosPuntos);
        estudiante.setNivel(NivelParticipacion.determinarNivel(nuevosPuntos));

        estudianteRepository.save(estudiante);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return estudianteRepository.existsByCorreo(correo);
    }
}
