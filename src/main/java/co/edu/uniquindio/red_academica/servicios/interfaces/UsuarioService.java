package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.modelo.documentos.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> obtenerPorId(String id);

    List<Usuario> obtenerTodos();

    Usuario actualizar(String id, Usuario usuario) throws Exception;

    void eliminar(String id) throws Exception;

    boolean existePorCorreo(String correo);
}
