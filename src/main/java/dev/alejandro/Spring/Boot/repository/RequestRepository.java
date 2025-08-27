package dev.alejandro.spring.boot.repository;

import dev.alejandro.spring.boot.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // Obtener todas las solicitudes ordenadas por fecha de creación ASC
    List<Request> findAllByOrderByFechaSolicitudAsc();

    // Obtener todas las solicitudes pendientes
    List<Request> findByAtendidaFalse();

    // Obtener todas las solicitudes atendidas
    List<Request> findByAtendidaTrue();
}