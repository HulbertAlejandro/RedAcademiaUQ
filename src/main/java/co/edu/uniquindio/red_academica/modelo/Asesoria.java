package co.edu.uniquindio.red_academica.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
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
    private String codigo;
    
    private ObjectId idEstudiante;
    private ObjectId idMentor;
    private LocalDateTime fecha;
    private EstadoAsesoria estado;
    private DetalleAsesoria detalle;
}
