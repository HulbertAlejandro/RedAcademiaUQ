package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import co.edu.uniquindio.red_academica.modelo.documentos.Chat.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensajeRepository extends MongoRepository<Chat.Mensaje, String> {

    List<Chat.Mensaje> findByChatIdOrderByFechaAsc(String chatId);

    List<Chat.Mensaje> findByRemitenteId(String remitenteId);

    List<Chat.Mensaje> findByDestinatarioId(String destinatarioId);

    List<Chat.Mensaje> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Chat.Mensaje> findByChatIdAndRemitenteId(String chatId, String remitenteId);

    List<Chat.Mensaje> findByChatIdAndDestinatarioId(String chatId, String destinatarioId);
}
