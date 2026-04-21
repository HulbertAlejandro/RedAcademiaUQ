package co.edu.uniquindio.red_academica.modelo.documentos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjuntoRespuesta {

    private String nombreArchivo;
    private String contentType;
    private Long tamanoBytes;
    private String archivoId;
}