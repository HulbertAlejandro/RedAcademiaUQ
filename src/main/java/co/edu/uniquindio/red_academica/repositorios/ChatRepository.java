package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByUsuario1IdOrUsuario2Id(String usuario1Id, String usuario2Id);

    List<Chat> findByUsuario1Id(String id);

    List<Chat> findByUsuario2Id(String id);

    Optional<Chat> findByUsuario1IdAndUsuario2Id(String usuario1Id, String usuario2Id);

    Optional<Chat> findByUsuario2IdAndUsuario1Id(String usuario2Id, String usuario1Id);
}