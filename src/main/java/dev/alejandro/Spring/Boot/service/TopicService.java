package dev.alejandro.spring.boot.service;

import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.entity.Topic;
import dev.alejandro.spring.boot.exception.topic.TopicNotFoundException;
import dev.alejandro.spring.boot.repository.TopicRepository;
import dev.alejandro.spring.boot.util.EntityMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    // Obtener todos los temas
    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll()
                .stream()
                .map(EntityMapper::toTopicDTO)
                .collect(Collectors.toList());
    }

    // Obtener un tema por ID
    public TopicDTO getTopicById(Long id) {
        return topicRepository.findById(id)
                .map(EntityMapper::toTopicDTO)
                .orElseThrow(() -> new TopicNotFoundException("Tema no encontrado con id " + id));
    }

    // Crear un nuevo tema
    public TopicDTO createTopic(TopicDTO dto) {
        Topic topic = EntityMapper.toTopicEntity(dto);
        Topic saved = topicRepository.save(topic);
        return EntityMapper.toTopicDTO(saved);
    }

    // Actualizar un tema existente
    public TopicDTO updateTopic(Long id, TopicDTO dto) {
        return topicRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(dto.getNombre());
                    Topic updated = topicRepository.save(existing);
                    return EntityMapper.toTopicDTO(updated);
                })
                .orElseThrow(() -> new TopicNotFoundException("Tema no encontrado con id " + id));
    }

    // Eliminar un tema
    public boolean deleteTopic(Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return true;
        }
        throw new TopicNotFoundException("Tema no encontrado con id " + id);
    }
}