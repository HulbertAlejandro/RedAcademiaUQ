package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByEstudiante1IdOrEstudiante2Id(String estudiante1Id, String estudiante2Id);

    List<Chat> findByEstudiante1Id(String id);

    List<Chat> findByEstudiante2Id(String id);

    Optional<Chat> findByEstudiante1IdAndEstudiante2Id(String estudiante1Id, String estudiante2Id);

    Optional<Chat> findByEstudiante2IdAndEstudiante1Id(String estudiante2Id, String estudiante1Id);
}