package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Document(collection = "estudiantes")
public class Estudiante extends Usuario {

    @Field("puntos_participacion")
    private int puntosParticipacion;

    private NivelParticipacion nivel;

    @Field("contenidos_subidos")
    private List<String> contenidosSubidos;

    private List<String> amigos;

    @Field("grupos_estudio")
    private List<String> gruposEstudio;

    @Field("grupos_rechazados")
    private List<String> gruposRechazados;

    @Field("codigo_recuperacion")
    private String codigoRecuperacion;

    @Field("fecha_expiracion_codigo_recuperacion")
    private LocalDateTime fechaExpiracionCodigoRecuperacion;
}