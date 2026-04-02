package co.edu.uniquindio.red_academica.modelo.documentos;

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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "chats")
public class Chat {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field("estudiante1_id")
    private String estudiante1Id;

    @Field("estudiante2_id")
    private String estudiante2Id;

    private List<Chat.Mensaje> mensajes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class Mensaje {
        private String remitenteId;
        private String destinatarioId;
        private String contenido;
        private LocalDateTime fecha;
    }
}
