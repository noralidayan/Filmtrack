package com.filmtrack.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Serie extends ContenidoAudiovisual {

    private int temporadas;
    private int capitulosPorTemporada;

    public Serie() {
        super();
    }

    public Serie(String nombre, LocalDate fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto,
                 int temporadas, int capitulosPorTemporada) {
        super(nombre, fechaLanzamiento, puntuacionEnEstrellas, genero, reparto);
        this.temporadas = temporadas;
        this.capitulosPorTemporada = capitulosPorTemporada;
    }

    // Getters y Setters
    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public int getCapitulosPorTemporada() {
        return capitulosPorTemporada;
    }

    public void setCapitulosPorTemporada(int capitulosPorTemporada) {
        this.capitulosPorTemporada = capitulosPorTemporada;
    }

    @Override
    public String toString() {
        return getNombre() + " - " + temporadas + " temporadas, " +
                capitulosPorTemporada + " capítulos por temporada (" +
                (getPuntuacionEnEstrellas() == 0 ? "sin puntuar" : getPuntuacionEnEstrellas() + "⭐") + ")";
    }
}