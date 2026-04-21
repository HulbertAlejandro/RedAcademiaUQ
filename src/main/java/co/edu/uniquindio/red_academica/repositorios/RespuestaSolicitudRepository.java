package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.RespuestaSolicitud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaSolicitudRepository extends MongoRepository<RespuestaSolicitud, String> {

    List<RespuestaSolicitud> findBySolicitudIdOrderByFechaCreacionAsc(String solicitudId);

    List<RespuestaSolicitud> findByAutorId(String autorId);
}