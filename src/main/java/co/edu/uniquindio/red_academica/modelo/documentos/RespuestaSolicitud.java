package co.edu.uniquindio.red_academica.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "respuestas_solicitud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaSolicitud {

    @Id
    private String id;

    @Field("solicitud_id")
    private String solicitudId;

    @Field("autor_id")
    private String autorId;

    @Field("autor_nombre")
    private String autorNombre;

    private String comentario;

    @Field("texto_respuesta")
    private String textoRespuesta;

    @Field("contenido_academico_id")
    private String contenidoAcademicoId;

    private List<AdjuntoRespuesta> adjuntos;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Field("es_respuesta_final")
    private boolean esRespuestaFinal;
}