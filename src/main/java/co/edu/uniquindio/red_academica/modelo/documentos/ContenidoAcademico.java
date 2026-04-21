package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("contenidos_academicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContenidoAcademico {

    @Id
    private String id;

    private String titulo;
    private TEMA tema;
    private String autor;
    private TipoContenido tipoContenido;

    private String nombreArchivo;
    private String contentType;
    private Long tamanoBytes;
    private String archivoId;

    private LocalDateTime fechaCreacion;
}