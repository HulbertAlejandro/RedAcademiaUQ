package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearMentorDTO;
import co.edu.uniquindio.red_academica.dto.InformacionMentorDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Mentor;
import co.edu.uniquindio.red_academica.repositorios.MentorRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;

    @Autowired
    public MentorServiceImpl(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public String crear(CrearMentorDTO dto) throws Exception {
        if (existePorCorreo(dto.email())) {
            throw new Exception("El correo ya está registrado");
        }

        Mentor mentor = new Mentor();
        mentor.setId(java.util.UUID.randomUUID().toString());
        mentor.setNombre(dto.nombre());
        mentor.setCorreo(dto.email());
        mentor.setContrasena(dto.password());
        mentor.setEspecialidad(dto.especialidad());
        mentor.setHorariosDisponibles(new ArrayList<>());

        Mentor guardado = mentorRepository.save(mentor);
        return guardado.getId();
    }

    @Override
    public InformacionMentorDTO obtenerPorId(String id) throws Exception {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new Exception("Mentor no encontrado"));

        return new InformacionMentorDTO(
                mentor.getId(),
                mentor.getNombre(),
                mentor.getCorreo(),
                mentor.getEspecialidad(),
                mentor.getHorariosDisponibles() != null ? mentor.getHorariosDisponibles().size() : 0,
                0
        );
    }

    @Override
    public List<InformacionMentorDTO> obtenerTodos() {
        return mentorRepository.findAll().stream()
                .map(mentor -> new InformacionMentorDTO(
                        mentor.getId(),
                        mentor.getNombre(),
                        mentor.getCorreo(),
                        mentor.getEspecialidad(),
                        mentor.getHorariosDisponibles() != null ? mentor.getHorariosDisponibles().size() : 0,
                        0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public InformacionMentorDTO actualizar(String id, CrearMentorDTO dto) throws Exception {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new Exception("Mentor no encontrado"));

        if (!mentor.getCorreo().equals(dto.email()) && existePorCorreo(dto.email())) {
            throw new Exception("El correo ya está registrado");
        }

        mentor.setNombre(dto.nombre());
        mentor.setCorreo(dto.email());
        mentor.setContrasena(dto.password());
        mentor.setEspecialidad(dto.especialidad());

        Mentor actualizado = mentorRepository.save(mentor);
        return obtenerPorId(actualizado.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!mentorRepository.existsById(id)) {
            throw new Exception("Mentor no encontrado");
        }
        mentorRepository.deleteById(id);
    }

    @Override
    public List<InformacionMentorDTO> buscarPorEspecialidad(String especialidad) throws Exception {
        List<Mentor> mentores = mentorRepository.findByEspecialidadContainingIgnoreCase(especialidad);
        return mentores.stream()
                .map(mentor -> new InformacionMentorDTO(
                        mentor.getId(),
                        mentor.getNombre(),
                        mentor.getCorreo(),
                        mentor.getEspecialidad(),
                        mentor.getHorariosDisponibles() != null ? mentor.getHorariosDisponibles().size() : 0,
                        0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionMentorDTO> buscarPorNombre(String nombre) throws Exception {
        List<Mentor> mentores = mentorRepository.findByNombreContainingIgnoreCase(nombre);
        return mentores.stream()
                .map(mentor -> new InformacionMentorDTO(
                        mentor.getId(),
                        mentor.getNombre(),
                        mentor.getCorreo(),
                        mentor.getEspecialidad(),
                        mentor.getHorariosDisponibles() != null ? mentor.getHorariosDisponibles().size() : 0,
                        0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return mentorRepository.existsByCorreo(correo);
    }
}
