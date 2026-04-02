package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Valoracion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ValoracionRepository extends MongoRepository<Valoracion, String> {

    List<Valoracion> findByEstudianteId(String estudianteId);

    List<Valoracion> findByContenidoId(String contenidoId);

    List<Valoracion> findByPuntajeBetween(int minPuntaje, int maxPuntaje);

    List<Valoracion> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Valoracion> findByEstudianteIdAndContenidoId(String estudianteId, String contenidoId);
}
