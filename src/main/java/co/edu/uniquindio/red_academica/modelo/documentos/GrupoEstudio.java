package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "grupos_estudio")
public class GrupoEstudio {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;

    private TEMA tema;

    @Field("participantes")
    private List<String> participantes;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

    private String descripcion;
}