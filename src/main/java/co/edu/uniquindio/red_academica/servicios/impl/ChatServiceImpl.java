package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearChatDTO;
import co.edu.uniquindio.red_academica.dto.CrearMensajeDTO;
import co.edu.uniquindio.red_academica.dto.InformacionChatDTO;
import co.edu.uniquindio.red_academica.dto.InformacionMensajeDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Chat;
import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import co.edu.uniquindio.red_academica.modelo.documentos.Mentor;
import co.edu.uniquindio.red_academica.modelo.documentos.Mensaje;
import co.edu.uniquindio.red_academica.repositorios.ChatRepository;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.repositorios.MentorRepository;
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
    private final EstudianteRepository estudianteRepository;
    private final MentorRepository mentorRepository;

    @Autowired
    public ChatServiceImpl(
            ChatRepository chatRepository,
            MensajeRepository mensajeRepository,
            EstudianteRepository estudianteRepository,
            MentorRepository mentorRepository
    ) {
        this.chatRepository = chatRepository;
        this.mensajeRepository = mensajeRepository;
        this.estudianteRepository = estudianteRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    public String crear(CrearChatDTO dto) throws Exception {
        if (dto.usuario1Id().equals(dto.usuario2Id())) {
            throw new Exception("No puedes crear un chat contigo mismo");
        }

        Optional<Chat> chatExistente = chatRepository
                .findByUsuario1IdAndUsuario2Id(dto.usuario1Id(), dto.usuario2Id());

        if (chatExistente.isEmpty()) {
            chatExistente = chatRepository
                    .findByUsuario2IdAndUsuario1Id(dto.usuario1Id(), dto.usuario2Id());
        }

        if (chatExistente.isPresent()) {
            return chatExistente.get().getId();
        }

        Chat chat = new Chat();
        chat.setId(UUID.randomUUID().toString());
        chat.setUsuario1Id(dto.usuario1Id());
        chat.setUsuario2Id(dto.usuario2Id());
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
                .orElse(chat.getFechaCreacion());

        return new InformacionChatDTO(
                chat.getId(),
                chat.getUsuario1Id(),
                obtenerNombreUsuario(chat.getUsuario1Id()),
                chat.getUsuario2Id(),
                obtenerNombreUsuario(chat.getUsuario2Id()),
                mensajesDTO,
                ultimoMensaje
        );
    }

    @Override
    public List<InformacionChatDTO> obtenerPorUsuario(String usuarioId) throws Exception {
        List<Chat> chats = chatRepository.findByUsuario1IdOrUsuario2Id(usuarioId, usuarioId);

        return chats.stream()
                .map(chat -> {
                    List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaAsc(chat.getId());

                    List<InformacionMensajeDTO> mensajesDTO = mensajes.stream()
                            .map(this::convertirMensaje)
                            .collect(Collectors.toList());

                    LocalDateTime ultimoMensaje = mensajes.stream()
                            .map(Mensaje::getFecha)
                            .max(LocalDateTime::compareTo)
                            .orElse(chat.getFechaCreacion());

                    return new InformacionChatDTO(
                            chat.getId(),
                            chat.getUsuario1Id(),
                            obtenerNombreUsuario(chat.getUsuario1Id()),
                            chat.getUsuario2Id(),
                            obtenerNombreUsuario(chat.getUsuario2Id()),
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

        boolean remitenteValido =
                chat.getUsuario1Id().equals(dto.remitenteId()) ||
                        chat.getUsuario2Id().equals(dto.remitenteId());

        boolean destinatarioValido =
                chat.getUsuario1Id().equals(dto.destinatarioId()) ||
                        chat.getUsuario2Id().equals(dto.destinatarioId());

        if (!remitenteValido || !destinatarioValido) {
            throw new Exception("Los participantes no pertenecen a este chat");
        }

        if (dto.remitenteId().equals(dto.destinatarioId())) {
            throw new Exception("No puedes enviarte mensajes a ti mismo");
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
    public List<InformacionChatDTO> obtenerChatsEntreUsuarios(String usuario1Id, String usuario2Id) throws Exception {
        List<Chat> chats = chatRepository.findByUsuario1IdOrUsuario2Id(usuario1Id, usuario1Id);

        return chats.stream()
                .filter(chat ->
                        (chat.getUsuario1Id().equals(usuario1Id) && chat.getUsuario2Id().equals(usuario2Id)) ||
                                (chat.getUsuario1Id().equals(usuario2Id) && chat.getUsuario2Id().equals(usuario1Id))
                )
                .map(chat -> {
                    List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaAsc(chat.getId());

                    List<InformacionMensajeDTO> mensajesDTO = mensajes.stream()
                            .map(this::convertirMensaje)
                            .collect(Collectors.toList());

                    LocalDateTime ultimoMensaje = mensajes.stream()
                            .map(Mensaje::getFecha)
                            .max(LocalDateTime::compareTo)
                            .orElse(chat.getFechaCreacion());

                    return new InformacionChatDTO(
                            chat.getId(),
                            chat.getUsuario1Id(),
                            obtenerNombreUsuario(chat.getUsuario1Id()),
                            chat.getUsuario2Id(),
                            obtenerNombreUsuario(chat.getUsuario2Id()),
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
                obtenerNombreUsuario(mensaje.getRemitenteId()),
                mensaje.getDestinatarioId(),
                mensaje.getContenido(),
                mensaje.getFecha(),
                false
        );
    }

    private String obtenerNombreUsuario(String usuarioId) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(usuarioId);
        if (estudiante.isPresent()) {
            return estudiante.get().getNombre();
        }

        Optional<Mentor> mentor = mentorRepository.findById(usuarioId);
        if (mentor.isPresent()) {
            return mentor.get().getNombre();
        }

        return "Usuario";
    }
}