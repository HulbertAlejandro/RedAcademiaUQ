package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearChatDTO;
import co.edu.uniquindio.red_academica.dto.CrearMensajeDTO;
import co.edu.uniquindio.red_academica.dto.InformacionChatDTO;
import co.edu.uniquindio.red_academica.dto.InformacionMensajeDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import co.edu.uniquindio.red_academica.modelo.documentos.Mensaje;
import co.edu.uniquindio.red_academica.repositorios.ChatRepository;
import co.edu.uniquindio.red_academica.repositorios.MensajeRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

        Optional<Chat> chatExistente = chatRepository
                .findByEstudiante1IdAndEstudiante2Id(dto.estudiante1Id(), dto.estudiante2Id());

        if (chatExistente.isEmpty()) {
            chatExistente = chatRepository
                    .findByEstudiante2IdAndEstudiante1Id(dto.estudiante1Id(), dto.estudiante2Id());
        }

        if (chatExistente.isPresent()) {
            throw new Exception("Ya existe un chat entre estos estudiantes");
        }

        Chat chat = new Chat();
        chat.setId(UUID.randomUUID().toString());
        chat.setEstudiante1Id(dto.estudiante1Id());
        chat.setEstudiante2Id(dto.estudiante2Id());
        chat.setFechaCreacion(LocalDateTime.now());

        Chat guardado = chatRepository.save(chat);
        return guardado.getId();
    }

    @Override
    public InformacionChatDTO obtenerPorId(String id) throws Exception {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new Exception("Chat no encontrado"));

        List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaAsc(chat.getId());

        List<InformacionMensajeDTO> mensajesDTO = mensajes.stream()
                .map(this::convertirMensaje)
                .collect(Collectors.toList());

        LocalDateTime ultimoMensaje = mensajes.stream()
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
        List<Chat> chats = chatRepository.findByEstudiante1IdOrEstudiante2Id(estudianteId, estudianteId);

        return chats.stream()
                .map(chat -> {
                    List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaAsc(chat.getId());

                    List<InformacionMensajeDTO> mensajesDTO = mensajes.stream()
                            .map(this::convertirMensaje)
                            .collect(Collectors.toList());

                    LocalDateTime ultimoMensaje = mensajes.stream()
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

        List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaAsc(id);
        if (!mensajes.isEmpty()) {
            mensajeRepository.deleteAll(mensajes);
        }

        chatRepository.deleteById(id);
    }

    @Override
    public void enviarMensaje(CrearMensajeDTO dto) throws Exception {
        Chat chat = chatRepository.findById(dto.chatId())
                .orElseThrow(() -> new Exception("Chat no encontrado"));

        if (!chat.getEstudiante1Id().equals(dto.remitenteId()) &&
                !chat.getEstudiante2Id().equals(dto.remitenteId())) {
            throw new Exception("No eres participante de este chat");
        }

        Mensaje mensaje = new Mensaje();
        mensaje.setId(UUID.randomUUID().toString());
        mensaje.setChatId(dto.chatId());
        mensaje.setRemitenteId(dto.remitenteId());
        mensaje.setDestinatarioId(dto.destinatarioId());
        mensaje.setContenido(dto.contenido());
        mensaje.setFecha(LocalDateTime.now());

        mensajeRepository.save(mensaje);
    }

    @Override
    public List<InformacionChatDTO> obtenerChatsEntreEstudiantes(String estudiante1Id, String estudiante2Id) throws Exception {
        return chatRepository.findAll().stream()
                .filter(chat ->
                        (chat.getEstudiante1Id().equals(estudiante1Id) && chat.getEstudiante2Id().equals(estudiante2Id)) ||
                                (chat.getEstudiante1Id().equals(estudiante2Id) && chat.getEstudiante2Id().equals(estudiante1Id))
                )
                .map(chat -> {
                    List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaAsc(chat.getId());

                    List<InformacionMensajeDTO> mensajesDTO = mensajes.stream()
                            .map(this::convertirMensaje)
                            .collect(Collectors.toList());

                    LocalDateTime ultimoMensaje = mensajes.stream()
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
                mensaje.getId(),
                mensaje.getRemitenteId(),
                "",
                mensaje.getDestinatarioId(),
                mensaje.getContenido(),
                mensaje.getFecha(),
                false
        );
    }
}