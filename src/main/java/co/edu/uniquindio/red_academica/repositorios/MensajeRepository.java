package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {

    List<Mensaje> findByChatIdOrderByFechaAsc(String chatId);

    List<Mensaje> findByRemitenteId(String remitenteId);

    List<Mensaje> findByDestinatarioId(String destinatarioId);

    List<Mensaje> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Mensaje> findByChatIdAndRemitenteId(String chatId, String remitenteId);

    List<Mensaje> findByChatIdAndDestinatarioId(String chatId, String destinatarioId);
}