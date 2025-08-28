package dev.alejandro.spring.boot.repository;

import dev.alejandro.spring.boot.entity.Topic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TopicRepositoryTests {

    @Autowired
    private TopicRepository topicRepository;

    @Test
    @DisplayName("Guardar y encontrar un Topic por ID")
    void whenSaveTopic_thenCanBeFoundById() {
        // Arrange
        Topic topic = new Topic("Hardware");

        // Act
        Topic saved = topicRepository.save(topic);
        Optional<Topic> found = topicRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Hardware");
    }

    @Test
    @DisplayName("Buscar un Topic por nombre")
    void whenFindByNombre_thenReturnsTopic() {
        // Arrange
        topicRepository.save(new Topic("Red"));

        // Act
        Topic found = topicRepository.findByNombre("Red");

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getNombre()).isEqualTo("Red");
    }
}