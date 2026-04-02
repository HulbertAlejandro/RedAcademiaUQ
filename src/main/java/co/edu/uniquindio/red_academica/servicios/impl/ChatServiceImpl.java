package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearChatDTO;
import co.edu.uniquindio.red_academica.dto.CrearMensajeDTO;
import co.edu.uniquindio.red_academica.dto.InformacionChatDTO;
import co.edu.uniquindio.red_academica.dto.InformacionMensajeDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import co.edu.uniquindio.red_academica.modelo.documentos.Chat.Mensaje;
import co.edu.uniquindio.red_academica.repositorios.ChatRepository;
import co.edu.uniquindio.red_academica.repositorios.MensajeRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MensajeRepository mensajeRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, MensajeRepository mensajeRepository) {
        this.chatRepository = chatRepository;
        this.mensajeRepository = mensajeRepository;
    }

    @Override
    public String crear(CrearChatDTO dto) throws Exception {
        if (dto.estudiante1Id().equals(dto.estudiante2Id())) {
            throw new Exception("No puedes crear un chat contigo mismo");
        }

        Optional<Chat> chatExistente = chatRepository.findAll().stream()
                .filter(chat -> (chat.getEstudiante1Id().equals(dto.estudiante1Id()) && chat.getEstudiante2Id().equals(dto.estudiante2Id())) ||
                        (chat.getEstudiante1Id().equals(dto.estudiante2Id()) && chat.getEstudiante2Id().equals(dto.estudiante1Id())))
                .findFirst();

        if (chatExistente.isPresent()) {
            throw new Exception("Ya existe un chat entre estos estudiantes");
        }

        Chat chat = new Chat();
        chat.setId(java.util.UUID.randomUUID().toString());
        chat.setEstudiante1Id(dto.estudiante1Id());
        chat.setEstudiante2Id(dto.estudiante2Id());
        chat.setMensajes(List.of());

        Chat guardado = chatRepository.save(chat);
        return guardado.getId();
    }

    @Override
    public InformacionChatDTO obtenerPorId(String id) throws Exception {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new Exception("Chat no encontrado"));

        List<InformacionMensajeDTO> mensajesDTO = chat.getMensajes().stream()
                .map(this::convertirMensaje)
                .collect(Collectors.toList());

        LocalDateTime ultimoMensaje = chat.getMensajes().stream()
                .map(Mensaje::getFecha)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        return new InformacionChatDTO(
                chat.getId(),
                chat.getEstudiante1Id(),
                "",
                chat.getEstudiante2Id(),
                "",
                mensajesDTO,
                ultimoMensaje
        );
    }

    @Override
    public List<InformacionChatDTO> obtenerPorEstudiante(String estudianteId) throws Exception {
        List<Chat> chats = chatRepository.findByEstudiante1IdOrEstudiante2Id(estudianteId);
        return chats.stream()
                .map(chat -> {
                    List<InformacionMensajeDTO> mensajesDTO = chat.getMensajes().stream()
                            .map(this::convertirMensaje)
                            .collect(Collectors.toList());
                    
                    LocalDateTime ultimoMensaje = chat.getMensajes().stream()
                            .map(Mensaje::getFecha)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);
                    
                    return new InformacionChatDTO(
                            chat.getId(),
                            chat.getEstudiante1Id(),
                            "",
                            chat.getEstudiante2Id(),
                            "",
                            mensajesDTO,
                            ultimoMensaje
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!chatRepository.existsById(id)) {
            throw new Exception("Chat no encontrado");
        }
        chatRepository.deleteById(id);
    }

    @Override
    public void enviarMensaje(CrearMensajeDTO dto) throws Exception {
        Chat chat = chatRepository.findById(dto.chatId())
                .orElseThrow(() -> new Exception("Chat no encontrado"));

        if (!chat.getEstudiante1Id().equals(dto.remitenteId()) && !chat.getEstudiante2Id().equals(dto.remitenteId())) {
            throw new Exception("No eres participante de este chat");
        }

        Mensaje mensaje = new Mensaje();
        mensaje.setRemitenteId(dto.remitenteId());
        mensaje.setDestinatarioId(dto.destinatarioId());
        mensaje.setContenido(dto.contenido());
        mensaje.setFecha(LocalDateTime.now());

        List<Mensaje> mensajes = chat.getMensajes();
        if (mensajes == null) {
            mensajes = List.of();
        }
        mensajes.add(mensaje);
        chat.setMensajes(mensajes);

        chatRepository.save(chat);
    }

    @Override
    public List<InformacionChatDTO> obtenerChatsEntreEstudiantes(String estudiante1Id, String estudiante2Id) throws Exception {
        List<Chat> chats = chatRepository.findAll().stream()
                .filter(chat -> (chat.getEstudiante1Id().equals(estudiante1Id) && chat.getEstudiante2Id().equals(estudiante2Id)) ||
                        (chat.getEstudiante1Id().equals(estudiante2Id) && chat.getEstudiante2Id().equals(estudiante1Id)))
                .collect(Collectors.toList());

        return chats.stream()
                .map(chat -> {
                    List<InformacionMensajeDTO> mensajesDTO = chat.getMensajes().stream()
                            .map(this::convertirMensaje)
                            .collect(Collectors.toList());
                    
                    LocalDateTime ultimoMensaje = chat.getMensajes().stream()
                            .map(Mensaje::getFecha)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);
                    
                    return new InformacionChatDTO(
                            chat.getId(),
                            chat.getEstudiante1Id(),
                            "",
                            chat.getEstudiante2Id(),
                            "",
                            mensajesDTO,
                            ultimoMensaje
                    );
                })
                .collect(Collectors.toList());
    }

    private InformacionMensajeDTO convertirMensaje(Mensaje mensaje) {
        return new InformacionMensajeDTO(
                "",
                mensaje.getRemitenteId(),
                "",
                mensaje.getDestinatarioId(),
                mensaje.getContenido(),
                mensaje.getFecha(),
                false
        );
    }
}
