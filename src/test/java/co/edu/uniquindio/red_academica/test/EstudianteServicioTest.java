package co.edu.uniquindio.red_academica.test;

import co.edu.uniquindio.red_academica.dto.CrearEstudianteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EstudianteServicioTest {

    @Autowired
    private EstudianteServicio estudianteServicio;

    @Autowired
    private EstudianteRepo estudianteRepo;

    @BeforeEach
    public void setup() {
        // Limpiamos la base de datos antes de cada test para evitar errores de duplicados
        estudianteRepo.deleteAll();
    }

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

        // Verificar que no lance excepciones al crear por primera vez
        assertDoesNotThrow(() -> {
            String id = estudianteServicio.crearEstudiante(estudianteDTO);

            // Verificar que retorne un ID válido
            assertNotNull(id);
            assertFalse(id.isEmpty());
        });
    }

    @Test
    public void crearEstudianteEmailDuplicadoTest() {
        // 1. Registramos el primer estudiante
        CrearEstudianteDTO estudianteDTO1 = new CrearEstudianteDTO(
                "987654321",
                "María García",
                "maria.garcia@uq.edu.co",
                "Ingeniería Industrial",
                "password123"
        );

        assertDoesNotThrow(() -> estudianteServicio.crearEstudiante(estudianteDTO1));

        // 2. Intentar crear otro con el MISMO email
        CrearEstudianteDTO estudianteDTO2 = new CrearEstudianteDTO(
                "456789123",
                "Ana Martínez",
                "maria.garcia@uq.edu.co", // Email duplicado
                "Administración",
                "password456"
        );

        // 3. Verificamos que el sistema EFECTIVAMENTE lance una excepción
        assertThrows(Exception.class, () -> {
            estudianteServicio.crearEstudiante(estudianteDTO2);
        });
    }

    @Test
    public void crearEstudianteCedulaDuplicadaTest() {
        // 1. Registramos el primer estudiante
        CrearEstudianteDTO estudianteDTO1 = new CrearEstudianteDTO(
                "111222333",
                "Carlos Rodríguez",
                "carlos.rodriguez@uq.edu.co",
                "Ingeniería Civil",
                "password123"
        );

        assertDoesNotThrow(() -> estudianteServicio.crearEstudiante(estudianteDTO1));

        // 2. Intentar crear otro con la MISMA cédula
        CrearEstudianteDTO estudianteDTO2 = new CrearEstudianteDTO(
                "111222333", // Cédula duplicada
                "Luis Torres",
                "luis.torres@uq.edu.co", // Email diferente, pero cédula igual
                "Ingeniería Mecánica",
                "password456"
        );

        // 3. Verificamos que lance la excepción de cédula duplicada
        assertThrows(Exception.class, () -> {
            estudianteServicio.crearEstudiante(estudianteDTO2);
        });
    }
}