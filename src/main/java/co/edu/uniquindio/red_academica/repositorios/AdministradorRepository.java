package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.modelo.documentos.Administrador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends MongoRepository<Administrador, String> {
    Optional<Administrador> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}