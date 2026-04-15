package co.edu.uniquindio.red_academica.modelo.documentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "mensajes")
public class Mensaje {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field("chat_id")
    private String chatId;

    @Field("remitente_id")
    private String remitenteId;

    @Field("destinatario_id")
    private String destinatarioId;

    private String contenido;

    private LocalDateTime fecha;
}