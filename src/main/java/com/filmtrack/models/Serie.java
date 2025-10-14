package com.filmtrack.models;

import jakarta.persistence.Entity;
import java.util.List;

@Entity
public class Serie extends ContenidoAudiovisual {
    private int temporadas;
    private int capitulosPorTemporada;

    public Serie() {
    }

    public Serie(String nombre, String fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto, int temporadas, int capitulosPorTemporada) {
        super(nombre, fechaLanzamiento, puntuacionEnEstrellas, genero, reparto);
        this.temporadas = temporadas;
        this.capitulosPorTemporada = capitulosPorTemporada;
    }


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
        return "Serie{" +
                "temporadas=" + temporadas +
                ", capitulosPorTemporada=" + capitulosPorTemporada +
                '}';
    }
}
