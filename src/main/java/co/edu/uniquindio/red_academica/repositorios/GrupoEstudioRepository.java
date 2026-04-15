package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.GrupoEstudio;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GrupoEstudioRepository extends MongoRepository<GrupoEstudio, String> {

    List<GrupoEstudio> findByTema(TEMA tema);

    List<GrupoEstudio> findByParticipantesContaining(String estudianteId);

    List<GrupoEstudio> findByNombreContainingIgnoreCase(String nombre);

    @Query(" { $gte: [ { $size: \"$participantes\" }, ?0 ] }")
    List<GrupoEstudio> buscarConMinimoParticipantes(int minParticipantes);

    List<GrupoEstudio> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
}