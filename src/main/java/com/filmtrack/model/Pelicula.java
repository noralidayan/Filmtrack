package com.filmtrack.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Pelicula extends ContenidoAudiovisual {

    private int duracionEnMinutos;

    public Pelicula() {
        super();
    }

    public Pelicula(String nombre, LocalDate fechaLanzamiento, int puntuacionEnEstrellas, String genero,
                    List<Actor> reparto, int duracionEnMinutos) {
        super(nombre, fechaLanzamiento, puntuacionEnEstrellas, genero, reparto);
        this.duracionEnMinutos = duracionEnMinutos;
    }

    // Getter y Setter
    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + duracionEnMinutos + " min) - " +
                (getPuntuacionEnEstrellas() == 0 ? "sin puntuar" : getPuntuacionEnEstrellas() + "⭐");
    }
}