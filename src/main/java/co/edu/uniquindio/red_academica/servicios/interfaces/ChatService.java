package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearChatDTO;
import co.edu.uniquindio.red_academica.dto.CrearMensajeDTO;
import co.edu.uniquindio.red_academica.dto.InformacionChatDTO;

import java.util.List;

public interface ChatService {

    String crear(CrearChatDTO dto) throws Exception;

    InformacionChatDTO obtenerPorId(String id) throws Exception;

    List<InformacionChatDTO> obtenerPorUsuario(String usuarioId) throws Exception;

    void eliminar(String id) throws Exception;

    void enviarMensaje(CrearMensajeDTO dto) throws Exception;

    List<InformacionChatDTO> obtenerChatsEntreUsuarios(String usuario1Id, String usuario2Id) throws Exception;
}