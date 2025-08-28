package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // Listar todos los topics
    @GetMapping
    public List<TopicDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    // Obtener topic por ID
    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id) {
        TopicDTO dto = topicService.getTopicById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Crear topic
    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO dto) {
        TopicDTO created = topicService.createTopic(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Actualizar topic
    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id, @RequestBody TopicDTO dto) {
        TopicDTO updated = topicService.updateTopic(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Eliminar topic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        boolean deleted = topicService.deleteTopic(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}