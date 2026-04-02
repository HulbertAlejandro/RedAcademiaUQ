package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Asesoria;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsesoriaRepository extends MongoRepository<Asesoria, String> {

    List<Asesoria> findBySolicitanteId(String solicitanteId);

    List<Asesoria> findByAsesorId(String asesorId);

    List<Asesoria> findByEstado(EstadoAsesoria estado);

    List<Asesoria> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Asesoria> findBySolicitanteIdAndEstado(String solicitanteId, EstadoAsesoria estado);

    List<Asesoria> findByAsesorIdAndEstado(String asesorId, EstadoAsesoria estado);

    List<Asesoria> findByTemaContainingIgnoreCase(String tema);
}
