package co.edu.uniquindio.red_academica.test;

import co.edu.uniquindio.red_academica.dto.CrearEstudianteDTO;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstudianteServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EstudianteServicioTest {

    @Autowired
    private EstudianteServicio estudianteServicio;

    @Test
    public void crearEstudianteTest() {
        // Crear un DTO con datos válidos
        CrearEstudianteDTO estudianteDTO = new CrearEstudianteDTO(
                "123456789",
                "Juan Carlos Pérez",
                "juan.perez@uq.edu.co",
                "Ingeniería de Sistemas",
                "password123"
        );

        // Verificar que no lance excepciones al crear
        assertDoesNotThrow(() -> {
            String id = estudianteServicio.crearEstudiante(estudianteDTO);
            
            // Verificar que retorne un ID válido
            assertNotNull(id);
            assertFalse(id.isEmpty());
        });
    }

    @Test
    public void crearEstudianteEmailDuplicadoTest() {
        // Crear un estudiante primero
        CrearEstudianteDTO estudianteDTO1 = new CrearEstudianteDTO(
                "987654321",
                "María García",
                "maria.garcia@uq.edu.co",
                "Ingeniería Industrial",
                "password123"
        );

        assertDoesNotThrow(() -> {
            estudianteServicio.crearEstudiante(estudianteDTO1);
        });

        // Intentar crear otro estudiante con el mismo email
        CrearEstudianteDTO estudianteDTO2 = new CrearEstudianteDTO(
                "456789123",
                "Ana Martínez",
                "maria.garcia@uq.edu.co", // Email duplicado
                "Administración",
                "password456"
        );

        // Debe lanzar excepción por email duplicado
        assertThrows(Exception.class, () -> {
            estudianteServicio.crearEstudiante(estudianteDTO2);
        });
    }

    @Test
    public void crearEstudianteCedulaDuplicadaTest() {
        // Crear un estudiante primero
        CrearEstudianteDTO estudianteDTO1 = new CrearEstudianteDTO(
                "111222333",
                "Carlos Rodríguez",
                "carlos.rodriguez@uq.edu.co",
                "Ingeniería Civil",
                "password123"
        );

        assertDoesNotThrow(() -> {
            estudianteServicio.crearEstudiante(estudianteDTO1);
        });

        // Intentar crear otro estudiante con la misma cédula
        CrearEstudianteDTO estudianteDTO2 = new CrearEstudianteDTO(
                "111222333", // Cédula duplicada
                "Luis Torres",
                "luis.torres@uq.edu.co",
                "Ingeniería Mecánica",
                "password456"
        );

        // Debe lanzar excepción por cédula duplicada
        assertThrows(Exception.class, () -> {
            estudianteServicio.crearEstudiante(estudianteDTO2);
        });
    }
}
