package co.edu.uniquindio.red_academica.modelo.documentos;

import co.edu.uniquindio.red_academica.modelo.enums.TEMA;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoSolicitud;
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
@Document(collection = "solicitudes_ayuda")
public class SolicitudAyuda {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private TEMA tema;

    private int urgencia;

    @Field("solicitante_id")
    private String solicitanteId;

    private String descripcion;

    private EstadoSolicitud estado;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Field("id_contenido_resuelto")
    private String idContenidoResuelto;
}
