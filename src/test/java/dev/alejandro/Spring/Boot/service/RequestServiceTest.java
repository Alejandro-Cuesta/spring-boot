package dev.alejandro.spring.boot.service;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;
import dev.alejandro.spring.boot.repository.RequestRepository;
import dev.alejandro.spring.boot.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RequestServiceTests {

    private RequestRepository requestRepository;
    private TopicRepository topicRepository;
    private RequestService requestService;

    @BeforeEach
    void setup() {
        requestRepository = Mockito.mock(RequestRepository.class);
        topicRepository = Mockito.mock(TopicRepository.class);
        requestService = new RequestService(requestRepository, topicRepository);
    }

    @Test
    @DisplayName("Crear una solicitud")
    void testCreateRequest() {
        Topic topic = new Topic(1L, "Hardware");
        RequestDTO dto = new RequestDTO();
        dto.setNombreSolicitante("Ana");
        dto.setDescripcion("PC no funciona");
        dto.setTemaId(1L);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(requestRepository.save(any(Request.class))).thenAnswer(inv -> inv.getArgument(0));

        RequestDTO result = requestService.createRequest(dto);

        assertThat(result).isNotNull();
        assertThat(result.getNombreSolicitante()).isEqualTo("Ana");
        assertThat(result.getTemaId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Marcar solicitud como atendida")
    void testMarkAsAttended() {
        Topic topic = new Topic(1L, "Software");
        Request request = new Request();
        request.setNombreSolicitante("Luis");
        request.setTema(topic);
        request.setAtendida(false);

        when(requestRepository.findById(10L)).thenReturn(Optional.of(request));
        when(requestRepository.save(any(Request.class))).thenAnswer(inv -> inv.getArgument(0));

        RequestDTO result = requestService.markAsAttended(10L, "Pedro");

        assertThat(result.getAtendida()).isTrue();
        assertThat(result.getNombreAtendio()).isEqualTo("Pedro");
        assertThat(result.getFechaAtencion()).isNotNull();
    }

    @Test
    @DisplayName("Obtener todas las solicitudes pendientes")
    void testGetPendingRequests() {
        Topic topic = new Topic(2L, "Red");
        Request r1 = new Request();
        r1.setNombreSolicitante("Ana");
        r1.setTema(topic);
        r1.setAtendida(false);

        when(requestRepository.findByAtendidaFalse()).thenReturn(Arrays.asList(r1));

        var result = requestService.getPendingRequests();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombreSolicitante()).isEqualTo("Ana");
    }

    @Test
    @DisplayName("Eliminar solicitud atendida")
    void testDeleteRequest() {
        Request request = new Request();
        request.setAtendida(true);

        when(requestRepository.findById(10L)).thenReturn(Optional.of(request));

        boolean deleted = requestService.deleteRequest(10L);

        assertThat(deleted).isTrue();
        verify(requestRepository, times(1)).deleteById(10L);
    }
}