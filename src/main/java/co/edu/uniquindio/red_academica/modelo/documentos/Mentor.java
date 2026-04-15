package co.edu.uniquindio.red_academica.modelo.documentos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "mentores")
public class Mentor extends Usuario {
    
    private String especialidad;
    
    @Field("horarios_disponibles")
    private List<String> horariosDisponibles;
}
