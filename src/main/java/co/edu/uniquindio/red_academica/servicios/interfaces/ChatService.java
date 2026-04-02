package co.edu.uniquindio.red_academica.servicios.interfaces;

import co.edu.uniquindio.red_academica.dto.CrearChatDTO;
import co.edu.uniquindio.red_academica.dto.CrearMensajeDTO;
import co.edu.uniquindio.red_academica.dto.InformacionChatDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import java.util.List;

public interface ChatService {

    String crear(CrearChatDTO dto) throws Exception;

    InformacionChatDTO obtenerPorId(String id) throws Exception;

    List<InformacionChatDTO> obtenerPorEstudiante(String estudianteId) throws Exception;

    void eliminar(String id) throws Exception;

    void enviarMensaje(CrearMensajeDTO dto) throws Exception;

    List<InformacionChatDTO> obtenerChatsEntreEstudiantes(String estudiante1Id, String estudiante2Id) throws Exception;
}
