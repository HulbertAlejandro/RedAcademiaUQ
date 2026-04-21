package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.SolicitudAyuda;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitudAyudaRepository extends MongoRepository<SolicitudAyuda, String> {

    List<SolicitudAyuda> findBySolicitanteId(String solicitanteId);

    List<SolicitudAyuda> findByTema(TEMA tema);

    List<SolicitudAyuda> findByEstado(EstadoSolicitud estado);

    List<SolicitudAyuda> findByUrgenciaGreaterThanEqualOrderByUrgenciaDesc(int urgencia);

    List<SolicitudAyuda> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<SolicitudAyuda> findByEstadoAndTema(EstadoSolicitud estado, TEMA tema);

    List<SolicitudAyuda> findBySolicitanteIdAndEstado(String solicitanteId, EstadoSolicitud estado);

    List<SolicitudAyuda> findByIdContenidoResueltoIsNotNull();
}