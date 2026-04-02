package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.NivelParticipacion;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
}
