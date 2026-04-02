package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "contenidos_academicos")
public class ContenidoAcademico {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String titulo;

    private TEMA tema;

    private String autor;

    private String contenido;

    private TipoContenido tipoContenido;

    private List<Valoracion> valoraciones;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

}
