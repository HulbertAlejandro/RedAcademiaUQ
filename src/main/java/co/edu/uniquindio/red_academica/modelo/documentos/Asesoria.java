package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
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
@Document(collection = "asesorias")
public class Asesoria {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field("solicitante_id")
    private String solicitanteId;

    @Field("asesor_id")
    private String asesorId;

    private String tema;

    @Field("fecha_hora")
    private LocalDateTime fechaHora;

    private String descripcion;

    private String medio;

    private EstadoAsesoria estado;
}
