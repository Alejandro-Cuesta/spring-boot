package dev.alejandro.spring.boot.dto;

import java.time.LocalDateTime;

public class RequestDTO {

    private Long id;
    private String nombreSolicitante;
    private LocalDateTime fechaSolicitud;
    private Long temaId;
    private String temaNombre;
    private String descripcion;
    private Boolean atendida;
    private String nombreAtendio;
    private LocalDateTime fechaAtencion;
    private LocalDateTime fechaEdicion;

    public RequestDTO() { }

    public RequestDTO(Long id,
                      String nombreSolicitante,
                      LocalDateTime fechaSolicitud,
                      Long temaId,
                      String temaNombre,
                      String descripcion,
                      Boolean atendida,
                      String nombreAtendio,
                      LocalDateTime fechaAtencion,
                      LocalDateTime fechaEdicion) {
        this.id = id;
        this.nombreSolicitante = nombreSolicitante;
        this.fechaSolicitud = fechaSolicitud;
        this.temaId = temaId;
        this.temaNombre = temaNombre;
        this.descripcion = descripcion;
        this.atendida = atendida;
        this.nombreAtendio = nombreAtendio;
        this.fechaAtencion = fechaAtencion;
        this.fechaEdicion = fechaEdicion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getTemaId() {
        return temaId;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }

    public String getTemaNombre() {
        return temaNombre;
    }

    public void setTemaNombre(String temaNombre) {
        this.temaNombre = temaNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getAtendida() {
        return atendida;
    }

    public void setAtendida(Boolean atendida) {
        this.atendida = atendida;
    }

    public String getNombreAtendio() {
        return nombreAtendio;
    }

    public void setNombreAtendio(String nombreAtendio) {
        this.nombreAtendio = nombreAtendio;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalDateTime getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(LocalDateTime fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }
}