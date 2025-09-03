package dev.alejandro.spring.boot.service;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;
import dev.alejandro.spring.boot.exception.RequestNotFoundException;
import dev.alejandro.spring.boot.exception.TopicNotFoundException;
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

    // Crear solicitud
    public RequestDTO createRequest(RequestDTO dto) {
        Topic tema = topicRepository.findById(dto.getTemaId())
                .orElseThrow(() -> new TopicNotFoundException("Tema no encontrado con id " + dto.getTemaId()));

        Request request = EntityMapper.toRequestEntity(dto, tema);
        // lógica que estaba en la entidad:
        request.setFechaSolicitud(LocalDateTime.now());
        request.setAtendida(false);

        return EntityMapper.toRequestDTO(requestRepository.save(request));
    }

    // Listar todas las solicitudes
    public List<RequestDTO> getAllRequests() {
        return requestRepository.findAll().stream()
                .map(EntityMapper::toRequestDTO)
                .collect(Collectors.toList());
    }

    // Editar solicitud
    public RequestDTO updateRequest(Long id, RequestDTO dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Solicitud no encontrada con id " + id));

        Topic tema = topicRepository.findById(dto.getTemaId())
                .orElseThrow(() -> new TopicNotFoundException("Tema no encontrado con id " + dto.getTemaId()));

        request.setNombreSolicitante(dto.getNombreSolicitante());
        request.setDescripcion(dto.getDescripcion());
        request.setTema(tema);
        request.setFechaEdicion(LocalDateTime.now());

        return EntityMapper.toRequestDTO(requestRepository.save(request));
    }

    // Marcar como atendida
    public RequestDTO markAsAttended(Long id, String nombreAtendio) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Solicitud no encontrada con id " + id));

        if (Boolean.TRUE.equals(request.getAtendida())) {
            throw new IllegalStateException("La solicitud ya fue atendida anteriormente");
        }

        request.setAtendida(true);
        request.setNombreAtendio(nombreAtendio);
        request.setFechaAtencion(LocalDateTime.now());

        return EntityMapper.toRequestDTO(requestRepository.save(request));
    }

    // Eliminar solicitud (solo si está atendida) Cambiado a boolean para que cuadre con los tests
    public boolean deleteRequest(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Solicitud no encontrada con id " + id));

        if (!Boolean.TRUE.equals(request.getAtendida())) {
            throw new IllegalStateException("No se puede eliminar una solicitud pendiente");
        }
        requestRepository.deleteById(id);
        return true;
    }

    // Listar solicitudes pendientes
    public List<RequestDTO> getPendingRequests() {
        return requestRepository.findByAtendidaFalse().stream()
                .map(EntityMapper::toRequestDTO)
                .collect(Collectors.toList());
    }
}
