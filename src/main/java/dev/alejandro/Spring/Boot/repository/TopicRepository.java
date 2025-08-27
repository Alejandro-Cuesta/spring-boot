package dev.alejandro.spring.boot.repository;

import dev.alejandro.spring.boot.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    // Ejemplo de query automática: buscar un tema por su nombre
    Topic findByNombre(String nombre);
}
