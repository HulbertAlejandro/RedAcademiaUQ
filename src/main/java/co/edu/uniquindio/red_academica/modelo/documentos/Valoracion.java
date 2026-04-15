package co.edu.uniquindio.red_academica.modelo.documentos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Valoracion {
    private String estudianteId;
    private int puntaje;
    private String comentario;
    private LocalDateTime fecha;
    private String contenidoId;
}