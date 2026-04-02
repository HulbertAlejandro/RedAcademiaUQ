package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByEstudiante1IdOrEstudiante2Id(String estudianteId);

    List<Chat> findByEstudiante1Id(String estudianteId);

    List<Chat> findByEstudiante2Id(String estudianteId);

    List<Chat> findByMensajesEstudianteIdContaining(String estudianteId);
}
