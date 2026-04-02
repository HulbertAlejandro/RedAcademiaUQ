package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.RecursoAcademico;
import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecursoAcademicoRepository extends MongoRepository<RecursoAcademico, String> {

    List<RecursoAcademico> findByAutor(String idAutor);

    List<RecursoAcademico> findByTipo(TipoContenido tipo);

    List<RecursoAcademico> findByNombreContainingIgnoreCase(String nombre);

    List<RecursoAcademico> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<RecursoAcademico> findByDescripcionContainingIgnoreCase(String descripcion);
}
