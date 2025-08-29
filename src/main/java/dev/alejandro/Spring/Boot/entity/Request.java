package dev.alejandro.spring.boot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreSolicitante;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic tema;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Boolean atendida = false;  // Inicializamos por defecto para evitar errores

    private String nombreAtendio;

    private LocalDateTime fechaAtencion;

    private LocalDateTime fechaEdicion;

    public Request() {
    }

    // Constructor corto para pruebas y facilidad de uso
    public Request(String nombreSolicitante, Topic tema, String descripcion) {
        this.nombreSolicitante = nombreSolicitante;
        this.tema = tema;
        this.descripcion = descripcion;
        this.atendida = false;  // Siempre inicializamos
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public Topic getTema() { return tema; }
    public void setTema(Topic tema) { this.tema = tema; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getAtendida() { return atendida; }
    public void setAtendida(Boolean atendida) { this.atendida = atendida; }

    public String getNombreAtendio() { return nombreAtendio; }
    public void setNombreAtendio(String nombreAtendio) { this.nombreAtendio = nombreAtendio; }

    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDateTime fechaAtencion) { this.fechaAtencion = fechaAtencion; }

    public LocalDateTime getFechaEdicion() { return fechaEdicion; }
    public void setFechaEdicion(LocalDateTime fechaEdicion) { this.fechaEdicion = fechaEdicion; }
}