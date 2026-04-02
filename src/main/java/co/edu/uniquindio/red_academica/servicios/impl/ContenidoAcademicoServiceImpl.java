package co.edu.uniquindio.red_academica.servicios.impl;

import co.edu.uniquindio.red_academica.dto.CrearContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.dto.CrearValoracionDTO;
import co.edu.uniquindio.red_academica.dto.InformacionContenidoAcademicoDTO;
import co.edu.uniquindio.red_academica.dto.InformacionValoracionDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.ContenidoAcademico;
import co.edu.uniquindio.red_academica.modelo.documentos.Valoracion;
import co.edu.uniquindio.red_academica.repositorios.ContenidoAcademicoRepository;
import co.edu.uniquindio.red_academica.repositorios.EstudianteRepository;
import co.edu.uniquindio.red_academica.servicios.interfaces.ContenidoAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContenidoAcademicoServiceImpl implements ContenidoAcademicoService {

    private final ContenidoAcademicoRepository contenidoRepository;
    private final EstudianteRepository estudianteRepository;

    @Autowired
    public ContenidoAcademicoServiceImpl(ContenidoAcademicoRepository contenidoRepository,
                                      EstudianteRepository estudianteRepository) {
        this.contenidoRepository = contenidoRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public String crear(CrearContenidoAcademicoDTO dto) throws Exception {
        ContenidoAcademico contenido = new ContenidoAcademico();
        contenido.setId(java.util.UUID.randomUUID().toString());
        contenido.setTitulo(dto.titulo());
        contenido.setTema(dto.tema());
        contenido.setAutor(dto.autor());
        contenido.setContenido(dto.contenido());
        contenido.setTipoContenido(dto.tipoContenido());
        contenido.setValoraciones(List.of());
        contenido.setFechaCreacion(LocalDateTime.now());

        ContenidoAcademico guardado = contenidoRepository.save(contenido);
        return guardado.getId();
    }

    @Override
    public InformacionContenidoAcademicoDTO obtenerPorId(String id) throws Exception {
        ContenidoAcademico contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new Exception("Contenido no encontrado"));

        List<InformacionValoracionDTO> valoracionesDTO = contenido.getValoraciones().stream()
                .map(this::convertirValoracion)
                .collect(Collectors.toList());

        double puntuacionPromedio = calcularPuntuacionPromedio(contenido.getValoraciones());

        return new InformacionContenidoAcademicoDTO(
                contenido.getId(),
                contenido.getTitulo(),
                contenido.getTema(),
                contenido.getAutor(),
                contenido.getContenido(),
                contenido.getTipoContenido(),
                valoracionesDTO,
                puntuacionPromedio,
                contenido.getFechaCreacion()
        );
    }

    @Override
    public List<InformacionContenidoAcademicoDTO> obtenerTodos() {
        return contenidoRepository.findAll().stream()
                .map(contenido -> {
                    List<InformacionValoracionDTO> valoracionesDTO = contenido.getValoraciones().stream()
                            .map(this::convertirValoracion)
                            .collect(Collectors.toList());
                    double puntuacionPromedio = calcularPuntuacionPromedio(contenido.getValoraciones());
                    
                    return new InformacionContenidoAcademicoDTO(
                            contenido.getId(),
                            contenido.getTitulo(),
                            contenido.getTema(),
                            contenido.getAutor(),
                            contenido.getContenido(),
                            contenido.getTipoContenido(),
                            valoracionesDTO,
                            puntuacionPromedio,
                            contenido.getFechaCreacion()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public InformacionContenidoAcademicoDTO actualizar(String id, CrearContenidoAcademicoDTO dto) throws Exception {
        ContenidoAcademico contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new Exception("Contenido no encontrado"));

        contenido.setTitulo(dto.titulo());
        contenido.setTema(dto.tema());
        contenido.setAutor(dto.autor());
        contenido.setContenido(dto.contenido());
        contenido.setTipoContenido(dto.tipoContenido());

        ContenidoAcademico actualizado = contenidoRepository.save(contenido);
        return obtenerPorId(actualizado.getId());
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!contenidoRepository.existsById(id)) {
            throw new Exception("Contenido no encontrado");
        }
        contenidoRepository.deleteById(id);
    }

    @Override
    public List<InformacionContenidoAcademicoDTO> buscarPorTema(co.edu.uniquindio.red_academica.modelo.enums.TEMA tema) throws Exception {
        List<ContenidoAcademico> contenidos = contenidoRepository.findByTema(tema);
        return contenidos.stream()
                .map(contenido -> {
                    List<InformacionValoracionDTO> valoracionesDTO = contenido.getValoraciones().stream()
                            .map(this::convertirValoracion)
                            .collect(Collectors.toList());
                    double puntuacionPromedio = calcularPuntuacionPromedio(contenido.getValoraciones());
                    
                    return new InformacionContenidoAcademicoDTO(
                            contenido.getId(),
                            contenido.getTitulo(),
                            contenido.getTema(),
                            contenido.getAutor(),
                            contenido.getContenido(),
                            contenido.getTipoContenido(),
                            valoracionesDTO,
                            puntuacionPromedio,
                            contenido.getFechaCreacion()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionContenidoAcademicoDTO> buscarPorAutor(String autorId) throws Exception {
        List<ContenidoAcademico> contenidos = contenidoRepository.findByAutor(autorId);
        return contenidos.stream()
                .map(contenido -> {
                    List<InformacionValoracionDTO> valoracionesDTO = contenido.getValoraciones().stream()
                            .map(this::convertirValoracion)
                            .collect(Collectors.toList());
                    double puntuacionPromedio = calcularPuntuacionPromedio(contenido.getValoraciones());
                    
                    return new InformacionContenidoAcademicoDTO(
                            contenido.getId(),
                            contenido.getTitulo(),
                            contenido.getTema(),
                            contenido.getAutor(),
                            contenido.getContenido(),
                            contenido.getTipoContenido(),
                            valoracionesDTO,
                            puntuacionPromedio,
                            contenido.getFechaCreacion()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionContenidoAcademicoDTO> buscarPorTipo(co.edu.uniquindio.red_academica.modelo.enums.TipoContenido tipo) throws Exception {
        List<ContenidoAcademico> contenidos = contenidoRepository.findByTipoContenido(tipo);
        return contenidos.stream()
                .map(contenido -> {
                    List<InformacionValoracionDTO> valoracionesDTO = contenido.getValoraciones().stream()
                            .map(this::convertirValoracion)
                            .collect(Collectors.toList());
                    double puntuacionPromedio = calcularPuntuacionPromedio(contenido.getValoraciones());
                    
                    return new InformacionContenidoAcademicoDTO(
                            contenido.getId(),
                            contenido.getTitulo(),
                            contenido.getTema(),
                            contenido.getAutor(),
                            contenido.getContenido(),
                            contenido.getTipoContenido(),
                            valoracionesDTO,
                            puntuacionPromedio,
                            contenido.getFechaCreacion()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InformacionContenidoAcademicoDTO> buscarPorTitulo(String titulo) throws Exception {
        List<ContenidoAcademico> contenidos = contenidoRepository.findByTituloContainingIgnoreCase(titulo);
        return contenidos.stream()
                .map(contenido -> {
                    List<InformacionValoracionDTO> valoracionesDTO = contenido.getValoraciones().stream()
                            .map(this::convertirValoracion)
                            .collect(Collectors.toList());
                    double puntuacionPromedio = calcularPuntuacionPromedio(contenido.getValoraciones());
                    
                    return new InformacionContenidoAcademicoDTO(
                            contenido.getId(),
                            contenido.getTitulo(),
                            contenido.getTema(),
                            contenido.getAutor(),
                            contenido.getContenido(),
                            contenido.getTipoContenido(),
                            valoracionesDTO,
                            puntuacionPromedio,
                            contenido.getFechaCreacion()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void agregarValoracion(CrearValoracionDTO dto) throws Exception {
        ContenidoAcademico contenido = contenidoRepository.findById(dto.contenidoId())
                .orElseThrow(() -> new Exception("Contenido no encontrado"));

        if (!estudianteRepository.existsById(dto.estudianteId())) {
            throw new Exception("Estudiante no encontrado");
        }

        if (dto.puntaje() < 1 || dto.puntaje() > 5) {
            throw new Exception("El puntaje debe estar entre 1 y 5");
        }

        List<Valoracion> valoraciones = contenido.getValoraciones();
        if (valoraciones == null) {
            valoraciones = List.of();
        }

        Optional<Valoracion> valoracionExistente = valoraciones.stream()
                .filter(v -> v.getEstudianteId().equals(dto.estudianteId()))
                .findFirst();

        if (valoracionExistente.isPresent()) {
            throw new Exception("El estudiante ya ha valorado este contenido");
        }

        Valoracion nuevaValoracion = new Valoracion();
        nuevaValoracion.setEstudianteId(dto.estudianteId());
        nuevaValoracion.setPuntaje(dto.puntaje());
        nuevaValoracion.setComentario(dto.comentario());
        nuevaValoracion.setFecha(LocalDateTime.now());

        valoraciones.add(nuevaValoracion);
        contenido.setValoraciones(valoraciones);

        contenidoRepository.save(contenido);

        int puntosGanados = dto.puntaje() * 2;
        estudianteRepository.findById(dto.estudianteId()).ifPresent(estudiante -> {
            int puntosActuales = estudiante.getPuntosParticipacion();
            estudiante.setPuntosParticipacion(puntosActuales + puntosGanados);
            estudianteRepository.save(estudiante);
        });
    }

    @Override
    public void guardarContenido(String estudianteId, String contenidoId) throws Exception {
        var estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));

        if (!contenidoRepository.existsById(contenidoId)) {
            throw new Exception("Contenido no encontrado");
        }

        List<String> contenidosGuardados = estudiante.getContenidosSubidos();
        if (contenidosGuardados == null) {
            contenidosGuardados = List.of();
        }

        if (contenidosGuardados.contains(contenidoId)) {
            throw new Exception("El contenido ya está guardado");
        }

        contenidosGuardados.add(contenidoId);
        estudiante.setContenidosSubidos(contenidosGuardados);
        estudianteRepository.save(estudiante);
    }

    private InformacionValoracionDTO convertirValoracion(Valoracion valoracion) {
        return new InformacionValoracionDTO(
                valoracion.getEstudianteId(),
                "",
                valoracion.getPuntaje(),
                valoracion.getComentario(),
                valoracion.getFecha()
        );
    }

    private double calcularPuntuacionPromedio(List<Valoracion> valoraciones) {
        if (valoraciones == null || valoraciones.isEmpty()) {
            return 0.0;
        }
        return valoraciones.stream()
                .mapToInt(Valoracion::getPuntaje)
                .average()
                .orElse(0.0);
    }
}
