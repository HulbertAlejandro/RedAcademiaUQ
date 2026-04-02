package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.GrupoEstudio;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoEstudioRepository extends MongoRepository<GrupoEstudio, String> {

    List<GrupoEstudio> findByTema(TEMA tema);

    List<GrupoEstudio> findByParticipantesContaining(String estudianteId);

    List<GrupoEstudio> findByNombreContainingIgnoreCase(String nombre);

    List<GrupoEstudio> findByParticipantesSizeGreaterThanEqual(int minParticipantes);

    List<GrupoEstudio> findByFechaCreacionBetween(java.time.LocalDateTime inicio, java.time.LocalDateTime fin);
}
