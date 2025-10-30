package com.filmtrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contenidoaudiovisual")
@Inheritance(strategy = InheritanceType.JOINED)
public class ContenidoAudiovisual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String genero = "Desconocido";
    private LocalDate fechaLanzamiento = LocalDate.of(1900, 1, 1);
    private boolean activo = true;
    private int puntuacionEnEstrellas;

    @ManyToMany
    @JoinTable(
            name = "contenido_reparto",
            joinColumns = @JoinColumn(name = "contenido_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> reparto = new ArrayList<>();

    public ContenidoAudiovisual() {}

    public ContenidoAudiovisual(String nombre) {
        this.nombre = nombre;
    }

    public ContenidoAudiovisual(String nombre, LocalDate fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.puntuacionEnEstrellas = puntuacionEnEstrellas;
        this.genero = genero != null ? genero : "Desconocido";
        this.reparto = reparto != null ? reparto : new ArrayList<>();
        this.activo = true;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getPuntuacionEnEstrellas() {
        return puntuacionEnEstrellas;
    }

    public void setPuntuacionEnEstrellas(int puntuacionEnEstrellas) {
        this.puntuacionEnEstrellas = puntuacionEnEstrellas;
    }

    public List<Actor> getReparto() {
        return reparto;
    }

    public void setReparto(List<Actor> reparto) {
        this.reparto = reparto;
    }

    @Override
    public String toString() {
        return nombre + " (" + (puntuacionEnEstrellas == 0 ? "sin puntuar" : puntuacionEnEstrellas + "⭐") + ")";
    }
}