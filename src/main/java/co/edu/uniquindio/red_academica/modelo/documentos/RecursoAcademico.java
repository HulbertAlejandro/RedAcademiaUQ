package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.TipoContenido;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "recursos")
public class RecursoAcademico {
    
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    
    private String nombre;
    
    private String descripcion;
    
    private TipoContenido tipo;
    
    @Field("url_archivo")
    private String urlArchivo;
    
    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Field("id_autor")
    private String idAutor;
}
