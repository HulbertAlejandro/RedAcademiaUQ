package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.config.JWTUtils;
import co.edu.uniquindio.red_academica.dto.*;
import co.edu.uniquindio.red_academica.modelo.documentos.Estudiante;
import co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.EstudianteService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private static final long CODIGO_RECUPERACION_EXPIRACION_MINUTOS = 15;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final EstudianteRepository estudianteRepository;
    private final JavaMailSender javaMailSender;
    private final boolean mailSenderAvailable;

    // 🔐 Encoder global
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JWTUtils jWTUtils;

    @Autowired
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository,
                                 ObjectProvider<JavaMailSender> javaMailSenderProvider, JWTUtils jWTUtils) {
        this.estudianteRepository = estudianteRepository;
        this.javaMailSender = javaMailSenderProvider.getIfAvailable();
        this.mailSenderAvailable = this.javaMailSender != null;
        this.jWTUtils = jWTUtils;
    }

    @Override
    public String crear(CrearEstudianteDTO dto) throws Exception {
        if (existePorCorreo(dto.email())) {
            throw new Exception("El correo ya está registrado");
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setId(java.util.UUID.randomUUID().toString());
        estudiante.setNombre(dto.nombre());
        estudiante.setCorreo(dto.email());

        // 🔐 ENCRIPTAR
        estudiante.setContrasena(passwordEncoder.encode(dto.password()));

        estudiante.setPuntosParticipacion(0);
        estudiante.setNivel(NivelParticipacion.determinarNivel(0));
        estudiante.setContenidosSubidos(new ArrayList<>());
        estudiante.setAmigos(new ArrayList<>());
        estudiante.setGruposEstudio(new ArrayList<>());
        estudiante.setGruposRechazados(new ArrayList<>());

        Estudiante guardado = estudianteRepository.save(estudiante);
        return guardado.getId();
    }

    @Override
    public InformacionEstudianteDTO obtenerPorId(String id) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        return new InformacionEstudianteDTO(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getCorreo(),
                estudiante.getNivel().toString(),
                estudiante.getPuntosParticipacion(),
                estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
        );
    }

    @Override
    public List<InformacionEstudianteDTO> obtenerTodos() {
        return estudianteRepository.findAll().stream()
                .map(estudiante -> new InformacionEstudianteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        estudiante.getNivel().toString(),
                        estudiante.getPuntosParticipacion(),
                        estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                        estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                        estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public InformacionEstudianteDTO actualizar(String id, CrearEstudianteDTO dto) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        if (!estudiante.getCorreo().equals(dto.email()) && existePorCorreo(dto.email())) {
            throw new Exception("El correo ya está registrado");
        }

        estudiante.setNombre(dto.nombre());
        estudiante.setCorreo(dto.email());

        // 🔐 ENCRIPTAR NUEVA CONTRASEÑA
        estudiante.setContrasena(passwordEncoder.encode(dto.password()));

        Estudiante actualizado = estudianteRepository.save(estudiante);
        return obtenerPorId(actualizado.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!estudianteRepository.existsById(id)) {
            throw new Exception("Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }

    @Override
    public void agregarAmigo(String estudianteId, String amigoId) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        Estudiante amigo = estudianteRepository.findById(amigoId)
                .orElseThrow(() -> new Exception("Amigo no encontrado"));

        if (estudianteId.equals(amigoId)) {
            throw new Exception("No puedes agregarte a ti mismo como amigo");
        }

        List<String> amigos = estudiante.getAmigos();
        if (amigos == null) {
            amigos = new ArrayList<>();
        } else {
            amigos = new ArrayList<>(amigos);
        }

        if (amigos.contains(amigoId)) {
            throw new Exception("El usuario ya es tu amigo");
        }

        amigos.add(amigoId);
        estudiante.setAmigos(amigos);
        estudianteRepository.save(estudiante);
    }

    @Override
    public void eliminarAmigo(String estudianteId, String amigoId) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        List<String> amigos = estudiante.getAmigos();
        if (amigos == null || !amigos.contains(amigoId)) {
            throw new Exception("El usuario no está en tu lista de amigos");
        }

        amigos = new ArrayList<>(amigos);
        amigos.remove(amigoId);
        estudiante.setAmigos(amigos);
        estudianteRepository.save(estudiante);
    }

    @Override
    public List<InformacionEstudianteDTO> buscarPorNombre(String nombre) throws Exception {
        List<Estudiante> estudiantes = estudianteRepository.findByNombreContainingIgnoreCase(nombre);
        return estudiantes.stream()
                .map(estudiante -> new InformacionEstudianteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        estudiante.getNivel().toString(),
                        estudiante.getPuntosParticipacion(),
                        estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                        estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                        estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionEstudianteDTO> obtenerPorNivel(NivelParticipacion nivel) throws Exception {
        List<Estudiante> estudiantes = estudianteRepository.findByNivel(nivel);
        return estudiantes.stream()
                .map(estudiante -> new InformacionEstudianteDTO(
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getCorreo(),
                        estudiante.getNivel().toString(),
                        estudiante.getPuntosParticipacion(),
                        estudiante.getAmigos() != null ? estudiante.getAmigos().size() : 0,
                        estudiante.getGruposEstudio() != null ? estudiante.getGruposEstudio().size() : 0,
                        estudiante.getContenidosSubidos() != null ? estudiante.getContenidosSubidos().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void agregarPuntosParticipacion(String estudianteId, int puntos) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        int nuevosPuntos = estudiante.getPuntosParticipacion() + puntos;
        estudiante.setPuntosParticipacion(nuevosPuntos);
        estudiante.setNivel(NivelParticipacion.determinarNivel(nuevosPuntos));

        estudianteRepository.save(estudiante);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return estudianteRepository.existsByCorreo(correo);
    }

    @Override
    public TokenDTO autenticar(LoginDTO dto) throws Exception {

        Estudiante estudiante = estudianteRepository.findByCorreo(dto.email())
                .orElseThrow(() -> new Exception("Correo o contraseña incorrectos"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(dto.password(), estudiante.getContrasena())) {
            throw new Exception("La contraseña es incorrecta");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", estudiante.getId());
        claims.put("nombre", estudiante.getNombre());
        claims.put("rol", "ESTUDIANTE");

        String token = jWTUtils.generarToken(estudiante.getCorreo(), claims);

        System.out.println("TOKEN GENERADO: " + token);

        return new TokenDTO(token);
    }

    @Override
    public void cambiarContrasena(String estudianteId, CambiarPasswordDTO dto) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        if (!passwordEncoder.matches(dto.passwordActual(), estudiante.getContrasena())) {
            throw new Exception("Contraseña actual incorrecta");
        }

        estudiante.setContrasena(passwordEncoder.encode(dto.passwordNueva()));
        estudianteRepository.save(estudiante);
    }

    @Override
    public String obtenerPorCorreo(ObtenerNombreDTO usuario) {
        Optional<Estudiante> estudiante = estudianteRepository.findByCorreo(usuario.correo());
        return estudiante.get().getNombre();
    }

    @Override
    public void iniciarRecuperacionPassword(String email) throws Exception {
        System.out.println("1. Iniciando recuperación para: " + email);

        Estudiante estudiante = estudianteRepository.findByCorreo(email)
                .orElseThrow(() -> new Exception("Correo no registrado"));

        System.out.println("2. Estudiante encontrado: " + estudiante.getCorreo());

        String codigo = generarCodigoRecuperacion();
        System.out.println("3. Código generado: " + codigo);

        estudiante.setCodigoRecuperacion(codigo);
        estudiante.setFechaExpiracionCodigoRecuperacion(
                LocalDateTime.now().plusMinutes(CODIGO_RECUPERACION_EXPIRACION_MINUTOS)
        );

        estudianteRepository.save(estudiante);
        System.out.println("4. Código guardado en BD");

        enviarCodigoRecuperacion(email, codigo);
        System.out.println("5. Correo enviado correctamente");
    }

    @Override
    public void verificarCodigoRecuperacion(String email, String codigo) throws Exception {
        Estudiante estudiante = estudianteRepository.findByCorreo(email)
                .orElseThrow(() -> new Exception("Correo no registrado"));

        if (estudiante.getCodigoRecuperacion() == null ||
                !estudiante.getCodigoRecuperacion().equals(codigo)) {
            throw new Exception("Código de recuperación inválido");
        }

        if (estudiante.getFechaExpiracionCodigoRecuperacion() == null ||
                estudiante.getFechaExpiracionCodigoRecuperacion().isBefore(LocalDateTime.now())) {
            throw new Exception("El código de recuperación ha expirado");
        }
    }

    @Override
    public void restablecerPassword(String email, String codigo, String passwordNueva) throws Exception {
        Estudiante estudiante = estudianteRepository.findByCorreo(email)
                .orElseThrow(() -> new Exception("Correo no registrado"));

        if (estudiante.getCodigoRecuperacion() == null ||
                !estudiante.getCodigoRecuperacion().equals(codigo)) {
            throw new Exception("Código de recuperación inválido");
        }

        if (estudiante.getFechaExpiracionCodigoRecuperacion() == null ||
                estudiante.getFechaExpiracionCodigoRecuperacion().isBefore(LocalDateTime.now())) {
            throw new Exception("El código de recuperación ha expirado");
        }

        // 🔐 ENCRIPTAR
        estudiante.setContrasena(passwordEncoder.encode(passwordNueva));

        estudiante.setCodigoRecuperacion(null);
        estudiante.setFechaExpiracionCodigoRecuperacion(null);
        estudianteRepository.save(estudiante);
    }

    private String generarCodigoRecuperacion() {
        int numero = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(numero);
    }

    private void enviarCodigoRecuperacion(String correo, String codigo) throws Exception {
        try {
            if (mailSenderAvailable) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("redacademicauq@gmail.com");
                message.setTo(correo);
                message.setSubject("Recuperación de contraseña Red Académica");
                message.setText("Tu código es: " + codigo);

                javaMailSender.send(message);
                System.out.println("5. Correo enviado correctamente");
            } else {
                System.out.println("[RECUPERACION] Código para " + correo + ": " + codigo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No fue posible enviar el correo de recuperación");
        }
    }
}