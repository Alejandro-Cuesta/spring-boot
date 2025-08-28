package dev.alejandro.spring.boot.service;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;
import dev.alejandro.spring.boot.repository.RequestRepository;
import dev.alejandro.spring.boot.repository.TopicRepository;
import dev.alejandro.spring.boot.util.EntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final TopicRepository topicRepository;

    public RequestService(RequestRepository requestRepository, TopicRepository topicRepository) {
        this.requestRepository = requestRepository;
        this.topicRepository = topicRepository;
    }

    // Obtener todas las solicitudes
    public List<RequestDTO> getAllRequests() {
        return requestRepository.findAllByOrderByFechaSolicitudAsc()
                .stream()
                .map(EntityMapper::toRequestDTO)
                .collect(Collectors.toList());
    }

    // Crear una nueva solicitud
    public RequestDTO createRequest(RequestDTO dto) {
        Topic topic = topicRepository.findById(dto.getTemaId())
                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));

        Request request = EntityMapper.toRequestEntity(dto, topic);
        Request saved = requestRepository.save(request);
        return EntityMapper.toRequestDTO(saved);
    }

    // Marcar solicitud como atendida
    public RequestDTO markAsAttended(Long requestId, String nombreAtendio) {
        return requestRepository.findById(requestId)
                .map(request -> {
                    request.setAtendida(true);
                    request.setNombreAtendio(nombreAtendio);
                    request.setFechaAtencion(LocalDateTime.now());
                    Request updated = requestRepository.save(request);
                    return EntityMapper.toRequestDTO(updated);
                }).orElse(null);
    }

    // Editar solicitud
    public RequestDTO updateRequest(Long requestId, RequestDTO dto) {
        return requestRepository.findById(requestId)
                .map(existing -> {
                    if (dto.getDescripcion() != null) existing.setDescripcion(dto.getDescripcion());
                    if (dto.getTemaId() != null) {
                        Topic topic = topicRepository.findById(dto.getTemaId())
                                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
                        existing.setTema(topic);
                    }
                    existing.setFechaEdicion(LocalDateTime.now());
                    Request updated = requestRepository.save(existing);
                    return EntityMapper.toRequestDTO(updated);
                }).orElse(null);
    }

    // Eliminar solicitud (solo si estaba atendida)
    public boolean deleteRequest(Long requestId) {
        return requestRepository.findById(requestId)
                .map(request -> {
                    if (Boolean.TRUE.equals(request.getAtendida())) {
                        requestRepository.deleteById(requestId);
                        return true;
                    }
                    return false;
                }).orElse(false);
    }

    // Obtener solicitudes pendientes
    public List<RequestDTO> getPendingRequests() {
        return requestRepository.findByAtendidaFalse()
                .stream()
                .map(EntityMapper::toRequestDTO)
                .collect(Collectors.toList());
    }
}