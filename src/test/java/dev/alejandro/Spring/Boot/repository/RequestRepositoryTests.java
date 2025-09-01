package dev.alejandro.spring.boot.repository;

import dev.alejandro.spring.boot.entity.Request;
import dev.alejandro.spring.boot.entity.Topic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RequestRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    @DisplayName("Guardar y encontrar Request por ID")
    void whenSaveRequest_thenCanBeFoundById() {
        Topic topic = topicRepository.save(new Topic("Software"));
        Request request = new Request("Ana", topic, "No funciona el PC");
        request.setFechaSolicitud(LocalDateTime.now());

        Request saved = requestRepository.save(request);

        assertThat(requestRepository.findById(saved.getId())).isPresent();
        Request found = requestRepository.findById(saved.getId()).get();
        assertThat(found.getNombreSolicitante()).isEqualTo("Ana");
        assertThat(found.getDescripcion()).isEqualTo("No funciona el PC");
    }

    @Test
    @DisplayName("Listar todas las solicitudes ordenadas por fecha")
    void whenFindAllByOrderByFechaSolicitudAsc_thenOrderedList() {
        Topic topic = topicRepository.save(new Topic("Impresora"));

        Request r1 = new Request("Luis", topic, "Problema con drivers");
        r1.setFechaSolicitud(LocalDateTime.now().minusHours(1));
        Request r2 = new Request("Marta", topic, "No imprime");
        r2.setFechaSolicitud(LocalDateTime.now());

        requestRepository.save(r1);
        requestRepository.save(r2);

        List<Request> requests = requestRepository.findAllByOrderByFechaSolicitudAsc();

        assertThat(requests).hasSize(2);
        assertThat(requests.get(0).getNombreSolicitante()).isEqualTo("Luis");
        assertThat(requests.get(0).getDescripcion()).isEqualTo("Problema con drivers");
        assertThat(requests.get(1).getNombreSolicitante()).isEqualTo("Marta");
        assertThat(requests.get(1).getDescripcion()).isEqualTo("No imprime");
    }

    @Test
    @DisplayName("Filtrar solicitudes pendientes y atendidas")
    void whenFindByAtendida_thenReturnsCorrectRequests() {
        Topic topic = topicRepository.save(new Topic("Red"));

        Request r1 = new Request("Ana", topic, "Wifi no conecta");
        r1.setFechaSolicitud(LocalDateTime.now());
        r1.setAtendida(false);

        Request r2 = new Request("Pedro", topic, "VPN caída");
        r2.setFechaSolicitud(LocalDateTime.now());
        r2.setAtendida(true);

        requestRepository.save(r1);
        requestRepository.save(r2);

        List<Request> pendientes = requestRepository.findByAtendidaFalse();
        List<Request> atendidas = requestRepository.findByAtendidaTrue();

        assertThat(pendientes).hasSize(1);
        assertThat(pendientes.get(0).getNombreSolicitante()).isEqualTo("Ana");
        assertThat(pendientes.get(0).getDescripcion()).isEqualTo("Wifi no conecta");

        assertThat(atendidas).hasSize(1);
        assertThat(atendidas.get(0).getNombreSolicitante()).isEqualTo("Pedro");
        assertThat(atendidas.get(0).getDescripcion()).isEqualTo("VPN caída");
    }
}