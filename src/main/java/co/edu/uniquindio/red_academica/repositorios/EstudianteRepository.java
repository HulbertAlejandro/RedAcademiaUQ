package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends MongoRepository<Estudiante, String> {

    Optional<Estudiante> findByCorreo(String correo);

    List<Estudiante> findByAmigosContaining(String amigoId);

    List<Estudiante> findByGruposEstudioContaining(String grupoId);

    List<Estudiante> findByContenidosSubidosContaining(String contenidoId);

    List<Estudiante> findByNivel(co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion nivel);

    List<Estudiante> findByPuntosParticipacionGreaterThanEqual(int puntos);

    boolean existsByCorreo(String correo);

    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
}
