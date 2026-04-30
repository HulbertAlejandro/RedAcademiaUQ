package co.edu.uniquindio.red_academica.repositorios;

import co.edu.uniquindio.red_academica.dto.EstadisticaAsesorDTO;
import co.edu.uniquindio.red_academica.dto.EstadisticaAsesoriaDTO;
import co.edu.uniquindio.red_academica.dto.EstadisticaMateriaDTO;
import co.edu.uniquindio.red_academica.modelo.documentos.Asesoria;
import co.edu.uniquindio.red_academica.modelo.enums.EstadoAsesoria;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsesoriaRepository extends MongoRepository<Asesoria, String> {

    List<Asesoria> findBySolicitanteId(String solicitanteId);

    List<Asesoria> findByAsesorId(String asesorId);

    List<Asesoria> findByEstado(EstadoAsesoria estado);

    List<Asesoria> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Asesoria> findBySolicitanteIdAndEstado(String solicitanteId, EstadoAsesoria estado);

    List<Asesoria> findByAsesorIdAndEstado(String asesorId, EstadoAsesoria estado);

    List<Asesoria> findByTemaContainingIgnoreCase(String tema);

    // ===============================
    // ESTADISTICAS
    // ===============================

    // 1. Materias (temas) más solicitadas
    @Aggregation(pipeline = {
            "{ $group: { _id: '$tema', total: { $sum: 1 } } }",
            "{ $project: { tema: '$_id', total: 1, _id: 0 } }",
            "{ $sort: { total: -1 } }"
    })
    List<EstadisticaMateriaDTO> materiasMasSolicitadas();

    // 2. Asesores más activos
    @Aggregation(pipeline = {

            "{ $match: { estado: 'FINALIZADA' } }",

            "{ $group: { _id: '$asesor_id', total: { $sum: 1 } } }",

            "{ $lookup: { " +
                    "from: 'mentores', " +
                    "localField: '_id', " +
                    "foreignField: '_id', " +
                    "as: 'mentor' " +
                    "} }",

            "{ $unwind: '$mentor' }",

            "{ $project: { " +
                    "nombre: '$mentor.nombre', " +
                    "total: 1, " +
                    "_id: 0 " +
                    "} }",

            "{ $sort: { total: -1 } }"

    })
    List<EstadisticaAsesorDTO> asesorConMasAsesorias();


    // 3. Asesorías agrupadas por estado
    @Aggregation(pipeline = {
            "{ $group: { _id: '$estado', total: { $sum: 1 } } }",
            "{ $project: { estado: '$_id', total: 1, _id: 0 } }"
    })
    List<EstadisticaAsesoriaDTO> asesoriasPorEstado();
}
