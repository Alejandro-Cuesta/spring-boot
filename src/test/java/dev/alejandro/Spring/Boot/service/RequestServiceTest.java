package dev.alejandro.spring.boot.service;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;
import dev.alejandro.spring.boot.exception.request.RequestNotFoundException;
import dev.alejandro.spring.boot.exception.topic.TopicNotFoundException;
import dev.alejandro.spring.boot.repository.RequestRepository;
import dev.alejandro.spring.boot.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private RequestService requestService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------ CREATE REQUEST ------------------
    @Test
    @DisplayName("Crear solicitud correctamente")
    void testCreateRequestSuccess() {
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

        verify(requestRepository, times(1)).save(any(Request.class));
    }

    @Test
    @DisplayName("Crear solicitud con tema inexistente lanza excepción")
    void testCreateRequestTopicNotFound() {
        RequestDTO dto = new RequestDTO();
        dto.setTemaId(99L);

        when(topicRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> requestService.createRequest(dto))
                .isInstanceOf(TopicNotFoundException.class)
                .hasMessageContaining("Tema no encontrado");
    }

    // ------------------ UPDATE REQUEST ------------------
    @Test
    @DisplayName("Actualizar solicitud correctamente")
    void testUpdateRequestSuccess() {
        Topic oldTopic = new Topic(1L, "Hardware");
        Topic newTopic = new Topic(2L, "Software");
        Request request = new Request();
        request.setNombreSolicitante("Ana");
        request.setDescripcion("Problema viejo");
        request.setTema(oldTopic);

        RequestDTO dto = new RequestDTO();
        dto.setNombreSolicitante("Ana María");
        dto.setDescripcion("Problema actualizado");
        dto.setTemaId(2L);

        when(requestRepository.findById(5L)).thenReturn(Optional.of(request));
        when(topicRepository.findById(2L)).thenReturn(Optional.of(newTopic));
        when(requestRepository.save(any(Request.class))).thenAnswer(inv -> inv.getArgument(0));

        RequestDTO result = requestService.updateRequest(5L, dto);

        assertThat(result.getNombreSolicitante()).isEqualTo("Ana María");
        assertThat(result.getDescripcion()).isEqualTo("Problema actualizado");
        assertThat(result.getTemaId()).isEqualTo(2L);
        assertThat(result.getFechaEdicion()).isNotNull();

        verify(requestRepository, times(1)).save(request);
    }

    @Test
    @DisplayName("Actualizar solicitud inexistente lanza excepción")
    void testUpdateRequestNotFound() {
        RequestDTO dto = new RequestDTO();
        dto.setTemaId(1L);

        when(requestRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> requestService.updateRequest(99L, dto))
                .isInstanceOf(RequestNotFoundException.class)
                .hasMessageContaining("Solicitud no encontrada");
    }

    @Test
    @DisplayName("Actualizar solicitud con tema inexistente lanza excepción")
    void testUpdateRequestTopicNotFound() {
        Topic oldTopic = new Topic(1L, "Hardware");
        Request request = new Request();
        request.setNombreSolicitante("Ana");
        request.setDescripcion("Problema viejo");
        request.setTema(oldTopic);

        RequestDTO dto = new RequestDTO();
        dto.setTemaId(99L);

        when(requestRepository.findById(5L)).thenReturn(Optional.of(request));
        when(topicRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> requestService.updateRequest(5L, dto))
                .isInstanceOf(TopicNotFoundException.class)
                .hasMessageContaining("Tema no encontrado");
    }

    // ------------------ MARK AS ATTENDED ------------------
    @Test
    @DisplayName("Marcar solicitud pendiente como atendida")
    void testMarkAsAttendedSuccess() {
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

        verify(requestRepository, times(1)).save(any(Request.class));
    }

    @Test
    @DisplayName("Marcar solicitud ya atendida lanza excepción")
    void testMarkAsAttendedAlreadyDone() {
        Request request = new Request();
        request.setAtendida(true);

        when(requestRepository.findById(20L)).thenReturn(Optional.of(request));

        assertThatThrownBy(() -> requestService.markAsAttended(20L, "Carlos"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ya fue atendida");

        verify(requestRepository, never()).save(any(Request.class));
    }

    @Test
    @DisplayName("Marcar solicitud inexistente lanza excepción")
    void testMarkAsAttendedNotFound() {
        when(requestRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> requestService.markAsAttended(99L, "Carlos"))
                .isInstanceOf(RequestNotFoundException.class)
                .hasMessageContaining("Solicitud no encontrada");
    }

    // ------------------ DELETE REQUEST ------------------
    @Test
    @DisplayName("Eliminar solicitud atendida correctamente")
    void testDeleteRequestSuccess() {
        Request request = new Request();
        request.setAtendida(true);

        when(requestRepository.findById(10L)).thenReturn(Optional.of(request));

        boolean deleted = requestService.deleteRequest(10L);

        assertThat(deleted).isTrue();
        verify(requestRepository, times(1)).deleteById(10L);
    }

    @Test
    @DisplayName("Eliminar solicitud pendiente lanza excepción")
    void testDeletePendingRequestThrowsException() {
        Request request = new Request();
        request.setAtendida(false);

        when(requestRepository.findById(11L)).thenReturn(Optional.of(request));

        assertThatThrownBy(() -> requestService.deleteRequest(11L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("pendiente");

        verify(requestRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Eliminar solicitud inexistente lanza excepción")
    void testDeleteRequestNotFound() {
        when(requestRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> requestService.deleteRequest(99L))
                .isInstanceOf(RequestNotFoundException.class)
                .hasMessageContaining("Solicitud no encontrada");

        verify(requestRepository, never()).deleteById(anyLong());
    }

    // ------------------ GET REQUESTS ------------------
    @Test
    @DisplayName("Obtener todas las solicitudes")
    void testGetAllRequests() {
        Topic topic = new Topic(1L, "Hardware");
        Request r1 = new Request("Ana", topic, "PC rota");
        Request r2 = new Request("Luis", topic, "Impresora rota");

        when(requestRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        var result = requestService.getAllRequests();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNombreSolicitante()).isEqualTo("Ana");
        assertThat(result.get(1).getNombreSolicitante()).isEqualTo("Luis");
    }

    @Test
    @DisplayName("Obtener todas las solicitudes cuando no hay ninguna")
    void testGetAllRequestsEmpty() {
        when(requestRepository.findAll()).thenReturn(Collections.emptyList());
        var result = requestService.getAllRequests();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Obtener solicitudes pendientes")
    void testGetPendingRequests() {
        Topic topic = new Topic(2L, "Red");
        Request r1 = new Request("Ana", topic, "Problema");
        r1.setAtendida(false);

        when(requestRepository.findByAtendidaFalse()).thenReturn(Arrays.asList(r1));

        var result = requestService.getPendingRequests();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombreSolicitante()).isEqualTo("Ana");
    }

    @Test
    @DisplayName("Obtener solicitudes pendientes cuando no hay ninguna")
    void testGetPendingRequestsEmpty() {
        when(requestRepository.findByAtendidaFalse()).thenReturn(Collections.emptyList());

        var result = requestService.getPendingRequests();
        assertThat(result).isEmpty();
    }
}