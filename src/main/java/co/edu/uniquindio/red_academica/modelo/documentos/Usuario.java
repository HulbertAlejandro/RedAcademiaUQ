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
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "usuarios")
public abstract class Usuario {
    
    @Id
    @EqualsAndHashCode.Include
    private String id;
    
    private String nombre;
    private String contrasena;
    private String correo;
}
