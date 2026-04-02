package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Mentor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends MongoRepository<Mentor, String> {

    Optional<Mentor> findByCorreo(String correo);

    List<Mentor> findByEspecialidadContainingIgnoreCase(String especialidad);

    List<Mentor> findByHorariosDisponiblesIsNotNull();

    boolean existsByCorreo(String correo);

    List<Mentor> findByNombreContainingIgnoreCase(String nombre);
}
