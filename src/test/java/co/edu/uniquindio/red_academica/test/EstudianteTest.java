package co.edu.uniquindio.red_academica.test;

import co.edu.uniquindio.red_academica.modelo.Estudiante;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EstudianteTest {

    @Autowired
    private EstudianteRepo estudianteRepo;

    @Test
    public void registrarTest() {
        // Crear un estudiante usando el patrón Builder
        Estudiante estudiante = Estudiante.builder()
                .codigo("EST001")
                .cedula("123456789")
                .nombre("Juan Pérez")
                .email("juan.perez@uq.edu.co")
                .programa("Ingeniería de Sistemas")
                .build();

        // Guardar el estudiante
        Estudiante guardado = estudianteRepo.save(estudiante);

        // Verificar que se guardó correctamente
        assertNotNull(guardado);
        assertNotNull(guardado.getCodigo());
        assertEquals("Juan Pérez", guardado.getNombre());
        assertEquals("juan.perez@uq.edu.co", guardado.getEmail());
    }

    @Test
    public void actualizarTest() {
        // Crear y guardar un estudiante
        Estudiante estudiante = Estudiante.builder()
                .codigo("EST002")
                .cedula("987654321")
                .nombre("María García")
                .email("maria.garcia@uq.edu.co")
                .programa("Ingeniería Industrial")
                .build();

        Estudiante guardado = estudianteRepo.save(estudiante);
        assertNotNull(guardado);

        // Buscar el estudiante por ID
        Estudiante encontrado = estudianteRepo.findById("EST002").orElse(null);
        assertNotNull(encontrado);

        // Modificar el nombre
        encontrado.setNombre("María García López");

        // Guardar los cambios
        Estudiante actualizado = estudianteRepo.save(encontrado);

        // Verificar el cambio
        assertEquals("María García López", actualizado.getNombre());
    }

    @Test
    public void eliminarTest() {
        // Crear y guardar un estudiante
        Estudiante estudiante = Estudiante.builder()
                .codigo("EST003")
                .cedula("456789123")
                .nombre("Carlos Rodríguez")
                .email("carlos.rodriguez@uq.edu.co")
                .programa("Administración de Empresas")
                .build();

        Estudiante guardado = estudianteRepo.save(estudiante);
        assertNotNull(guardado);

        // Verificar que existe
        Estudiante encontrado = estudianteRepo.findById("EST003").orElse(null);
        assertNotNull(encontrado);

        // Eliminar el estudiante
        estudianteRepo.deleteById("EST003");

        // Verificar que ya no existe
        Estudiante eliminado = estudianteRepo.findById("EST003").orElse(null);
        assertNull(eliminado);
    }
}
