package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.modelo.documentos.Usuario;
import co.edu.uniquindio.red_academica.repositorios.UsuarioRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> obtenerPorId(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizar(String id, Usuario usuario) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuario no encontrado");
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

}
