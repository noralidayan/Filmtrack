package com.filmtrack.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
@Entity
public class Pelicula extends ContenidoAudiovisual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int duracionEnMinutos;

    public Pelicula() {
    }

    public Pelicula(String nombre, String fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto, int duracionEnMinutos) {
        super(nombre, fechaLanzamiento, puntuacionEnEstrellas, genero, reparto);
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public Pelicula(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "duracionEnMinutos=" + duracionEnMinutos +
                '}';
    }
}
