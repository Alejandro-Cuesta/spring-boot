package dev.alejandro.spring.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "dev.alejandro.spring.boot.repository")
public class DatabaseConfig {
    // Actualmente dejamos vacío, Spring Boot se encarga de la configuración
    // Se puede usar para configurar cosas como:
    // - H2 Console
    // - Custom DataSource
    // - JPA properties avanzadas
}