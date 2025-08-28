package dev.alejandro.spring.boot.util;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMapperTests {

    @Test
    @DisplayName("Convertir Topic a TopicDTO y viceversa")
    void testTopicMapping() {
        Topic topic = new Topic(1L, "Hardware");

        TopicDTO dto = EntityMapper.toTopicDTO(topic);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNombre()).isEqualTo("Hardware");

        Topic entity = EntityMapper.toTopicEntity(dto);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getNombre()).isEqualTo("Hardware");
    }

    @Test
    @DisplayName("Convertir Request a RequestDTO y viceversa")
    void testRequestMapping() {
        Topic topic = new Topic(2L, "Software");

        Request request = new Request();
        request.setNombreSolicitante("Ana");
        request.setDescripcion("No funciona la app");
        request.setTema(topic);
        request.setAtendida(true);
        request.setNombreAtendio("Luis");
        request.setFechaSolicitud(LocalDateTime.of(2025, 8, 28, 10, 0));
        request.setFechaAtencion(LocalDateTime.of(2025, 8, 28, 12, 0));
        request.setFechaEdicion(LocalDateTime.of(2025, 8, 28, 11, 0));

        // Convertir a DTO
        RequestDTO dto = EntityMapper.toRequestDTO(request);

        assertThat(dto).isNotNull();
        assertThat(dto.getNombreSolicitante()).isEqualTo("Ana");
        assertThat(dto.getDescripcion()).isEqualTo("No funciona la app");
        assertThat(dto.getTemaId()).isEqualTo(topic.getId());
        assertThat(dto.getTemaNombre()).isEqualTo("Software");
        assertThat(dto.getAtendida()).isTrue();
        assertThat(dto.getNombreAtendio()).isEqualTo("Luis");
        assertThat(dto.getFechaSolicitud()).isEqualTo(LocalDateTime.of(2025, 8, 28, 10, 0));
        assertThat(dto.getFechaAtencion()).isEqualTo(LocalDateTime.of(2025, 8, 28, 12, 0));
        assertThat(dto.getFechaEdicion()).isEqualTo(LocalDateTime.of(2025, 8, 28, 11, 0));

        // Convertir de nuevo a entidad
        Request entity = EntityMapper.toRequestEntity(dto, topic);
        assertThat(entity).isNotNull();
        assertThat(entity.getNombreSolicitante()).isEqualTo("Ana");
        assertThat(entity.getDescripcion()).isEqualTo("No funciona la app");
        assertThat(entity.getTema()).isEqualTo(topic);
        assertThat(entity.getAtendida()).isTrue();
        assertThat(entity.getFechaSolicitud()).isEqualTo(LocalDateTime.of(2025, 8, 28, 10, 0));
        assertThat(entity.getFechaAtencion()).isEqualTo(LocalDateTime.of(2025, 8, 28, 12, 0));
        assertThat(entity.getFechaEdicion()).isEqualTo(LocalDateTime.of(2025, 8, 28, 11, 0));
    }
}