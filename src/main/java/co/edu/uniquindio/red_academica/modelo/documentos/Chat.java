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
@Document(collection = "chats")
public class Chat {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field("estudiante1_id")
    private String estudiante1Id;

    @Field("estudiante2_id")
    private String estudiante2Id;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;
}