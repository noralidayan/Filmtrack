package com.filmtrack.models;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ContenidoAudiovisual extends Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int fechaLanzamiento;
    private String genero;
    @ManyToMany
    @JoinTable(
            name = "contenido_reparto",
            joinColumns = @JoinColumn(name = "contenido_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> reparto = new ArrayList<>();

    private int puntuacionEnEstrellas;

    public ContenidoAudiovisual() {}

    public ContenidoAudiovisual(String nombre) {
        this.nombre = nombre;
    }

    public ContenidoAudiovisual(String nombre, String fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto) {
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getPuntuacionEnEstrellas() { return puntuacionEnEstrellas; }
    public void setPuntuacionEnEstrellas(int puntuacionEnEstrellas) { this.puntuacionEnEstrellas = puntuacionEnEstrellas; }

    @Override
    public String toString() {
        return nombre +
                (puntuacionEnEstrellas == 0 ? "sin puntuar" : puntuacionEnEstrellas) + ")";
    }

    public void setFechaLanzamiento(LocalDate of) {
    }
}