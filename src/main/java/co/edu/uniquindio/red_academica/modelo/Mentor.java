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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "mentores")
public class Mentor {
    
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    
    private String cedula;
    private String nombre;
    private String email;
    private String especialidad;
    private List<String> horariosDisponibles;
}
