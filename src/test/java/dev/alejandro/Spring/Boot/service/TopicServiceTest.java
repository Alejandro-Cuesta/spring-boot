package dev.alejandro.spring.boot.service;

import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.entity.Topic;
import dev.alejandro.spring.boot.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener todos los topics")
    void testGetAllTopics() {
        Topic t1 = new Topic(1L, "Hardware");
        Topic t2 = new Topic(2L, "Software");

        when(topicRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        var result = topicService.getAllTopics();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNombre()).isEqualTo("Hardware");
        assertThat(result.get(1).getNombre()).isEqualTo("Software");
    }

    @Test
    @DisplayName("Crear un topic")
    void testCreateTopic() {
        TopicDTO dto = new TopicDTO(null, "Red");
        Topic saved = new Topic(3L, "Red");

        when(topicRepository.save(any(Topic.class))).thenReturn(saved);

        TopicDTO result = topicService.createTopic(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getNombre()).isEqualTo("Red");
    }

    @Test
    @DisplayName("Actualizar un topic existente")
    void testUpdateTopic() {
        Topic existing = new Topic(1L, "OldName");
        when(topicRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(topicRepository.save(existing)).thenReturn(new Topic(1L, "NewName"));

        TopicDTO dto = new TopicDTO(null, "NewName");
        TopicDTO result = topicService.updateTopic(1L, dto);

        assertThat(result.getNombre()).isEqualTo("NewName");
    }

    @Test
    @DisplayName("Eliminar un topic")
    void testDeleteTopic() {
        when(topicRepository.existsById(1L)).thenReturn(true);

        boolean deleted = topicService.deleteTopic(1L);

        assertThat(deleted).isTrue();
        verify(topicRepository, times(1)).deleteById(1L);
    }
}