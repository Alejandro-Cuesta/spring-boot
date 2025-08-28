package dev.alejandro.spring.boot.util;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;

public class EntityMapper {

    // -----------------------
    // Topic <-> TopicDTO
    // -----------------------

    public static TopicDTO toTopicDTO(Topic topic) {
        if (topic == null) return null;
        return new TopicDTO(topic.getId(), topic.getNombre());
    }

    public static Topic toTopicEntity(TopicDTO dto) {
        if (dto == null) return null;
        return new Topic(dto.getId(), dto.getNombre());
    }

    // -----------------------
    // Request <-> RequestDTO
    // -----------------------

    public static RequestDTO toRequestDTO(Request request) {
        if (request == null) return null;

        return new RequestDTO(
                request.getId(),
                request.getNombreSolicitante(),
                request.getFechaSolicitud(),
                request.getTema() != null ? request.getTema().getId() : null,
                request.getTema() != null ? request.getTema().getNombre() : null,
                request.getDescripcion(),
                request.getAtendida(),
                request.getNombreAtendio(),
                request.getFechaAtencion(),
                request.getFechaEdicion()
        );
    }

    public static Request toRequestEntity(RequestDTO dto, Topic tema) {
        if (dto == null) return null;

        Request request = new Request();
        request.setNombreSolicitante(dto.getNombreSolicitante());
        request.setDescripcion(dto.getDescripcion());
        request.setTema(tema);

        // Si el DTO viene con datos de actualización
        request.setFechaSolicitud(dto.getFechaSolicitud());
        request.setAtendida(dto.getAtendida());
        request.setNombreAtendio(dto.getNombreAtendio());
        request.setFechaAtencion(dto.getFechaAtencion());
        request.setFechaEdicion(dto.getFechaEdicion());

        return request;
    }
}
