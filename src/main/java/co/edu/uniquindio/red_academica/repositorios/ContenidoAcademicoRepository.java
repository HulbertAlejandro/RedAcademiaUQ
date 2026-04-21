package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.ContenidoAcademico;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoAcademicoRepository extends MongoRepository<ContenidoAcademico, String> {

    List<ContenidoAcademico> findByTema(TEMA tema);

    List<ContenidoAcademico> findByTipoContenido(TipoContenido tipoContenido);

    List<ContenidoAcademico> findByTituloContainingIgnoreCase(String titulo);
}