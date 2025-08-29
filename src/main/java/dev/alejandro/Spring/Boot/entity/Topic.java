package dev.alejandro.spring.boot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    public Topic() { }

    //  Constructores de conveniencia (sin lógica)
    public Topic(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Topic(String nombre) {
        this.nombre = nombre;
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}