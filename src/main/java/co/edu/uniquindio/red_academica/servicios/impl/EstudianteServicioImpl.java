package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.modelo.Estudiante;
import co.edu.uniquindio.red_academica.modelo.EstadoUsuario;
import co.edu.uniquindio.red_academica.dto.CrearEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.EditarEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.InformacionEstudianteDTO;
import co.edu.uniquindio.red_academica.dto.ItemEstudianteDTO;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepo;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstudianteServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EstudianteServicioImpl implements EstudianteServicio {

    private final EstudianteRepo estudianteRepo;

    @Override
    public String crearEstudiante(CrearEstudianteDTO estudianteDTO) throws Exception {
        // Validar que el email no exista
        if (existeEmail(estudianteDTO.email())) {
            throw new Exception("El email ya está registrado");
        }

        // Validar que la cédula no exista
        if (existeCedula(estudianteDTO.cedula())) {
            throw new Exception("La cédula ya está registrada");
        }

        // Crear el estudiante usando Builder
        Estudiante estudiante = Estudiante.builder()
                .codigo(generarCodigo())
                .cedula(estudianteDTO.cedula())
                .nombre(estudianteDTO.nombre())
                .email(estudianteDTO.email())
                .programa(estudianteDTO.programa())
                .estado(EstadoUsuario.ACTIVO)
                .build();

        // Guardar el estudiante
        Estudiante guardado = estudianteRepo.save(estudiante);

        return guardado.getCodigo();
    }

    @Override
    public void editarEstudiante(EditarEstudianteDTO estudianteDTO) throws Exception {
        // Buscar el estudiante por ID
        Estudiante estudiante = estudianteRepo.findById(estudianteDTO.id())
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        // Validar que no esté eliminado
        if (estudiante.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new Exception("No se puede editar un estudiante eliminado");
        }

        // Actualizar los datos
        estudiante.setNombre(estudianteDTO.nombre());
        estudiante.setPrograma(estudianteDTO.programa());

        // Guardar los cambios
        estudianteRepo.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(String idEstudiante) throws Exception {
        // Buscar el estudiante por ID
        Estudiante estudiante = estudianteRepo.findById(idEstudiante)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        // Borrado lógico: cambiar estado a ELIMINADO
        estudiante.setEstado(EstadoUsuario.ELIMINADO);

        // Guardar los cambios
        estudianteRepo.save(estudiante);
    }

    @Override
    public InformacionEstudianteDTO obtenerInformacionEstudiante(String idEstudiante) throws Exception {
        // Buscar el estudiante por ID
        Estudiante estudiante = estudianteRepo.findById(idEstudiante)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        // Validar que no esté eliminado
        if (estudiante.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new Exception("Estudiante eliminado");
        }

        // Convertir a DTO
        return new InformacionEstudianteDTO(
                estudiante.getCodigo(),
                estudiante.getCedula(),
                estudiante.getNombre(),
                estudiante.getEmail(),
                estudiante.getPrograma(),
                estudiante.getEstado().toString()
        );
    }

    @Override
    public List<ItemEstudianteDTO> listarEstudiantes() throws Exception {
        // Obtener todos los estudiantes que no estén eliminados
        List<Estudiante> estudiantes = estudianteRepo.findAll()
                .stream()
                .filter(e -> e.getEstado() != EstadoUsuario.ELIMINADO)
                .collect(Collectors.toList());

        // Convertir a DTOs
        return estudiantes.stream()
                .map(e -> new ItemEstudianteDTO(e.getCodigo(), e.getNombre(), e.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public InformacionEstudianteDTO buscarEstudiantePorEmail(String email) throws Exception {
        // Buscar estudiante por email
        Estudiante estudiante = estudianteRepo.findAll()
                .stream()
                .filter(e -> e.getEmail().equals(email) && e.getEstado() != EstadoUsuario.ELIMINADO)
                .findFirst()
                .orElseThrow(() -> new Exception("Estudiante no encontrado con el email: " + email));

        // Convertir a DTO
        return new InformacionEstudianteDTO(
                estudiante.getCodigo(),
                estudiante.getCedula(),
                estudiante.getNombre(),
                estudiante.getEmail(),
                estudiante.getPrograma(),
                estudiante.getEstado().toString()
        );
    }

    @Override
    public boolean existeEmail(String email) throws Exception {
        return estudianteRepo.findAll()
                .stream()
                .anyMatch(e -> e.getEmail().equals(email) && e.getEstado() != EstadoUsuario.ELIMINADO);
    }

    @Override
    public boolean existeCedula(String cedula) throws Exception {
        return estudianteRepo.findAll()
                .stream()
                .anyMatch(e -> e.getCedula().equals(cedula) && e.getEstado() != EstadoUsuario.ELIMINADO);
    }

    private String generarCodigo() {
        return "EST-" + System.currentTimeMillis();
    }
}
