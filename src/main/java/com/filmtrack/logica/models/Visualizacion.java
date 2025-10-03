package com.filmtrack.logica.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Visualizacion {
    private ContenidoAudiovisual contenido;
    private LocalDate fechaVisto;
    private int puntuacion; // puntuación individual del usuario

    // Constructor con contenido y fecha
    public Visualizacion(ContenidoAudiovisual contenido, LocalDate fechaVisto) {
        this.contenido = contenido;
        this.fechaVisto = fechaVisto;
        this.puntuacion = 0; // por defecto sin puntuar
    }

    public ContenidoAudiovisual getContenido() {
        return contenido;
    }

    public void setContenido(ContenidoAudiovisual contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaVisto() {
        return fechaVisto;
    }

    public void setFechaVisto(LocalDate fechaVisto) {
        this.fechaVisto = fechaVisto;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        if (puntuacion >= 1 && puntuacion <= 5) {
            this.puntuacion = puntuacion;
        } else {
            System.out.println("La puntuación debe estar entre 1 y 5.");
        }
    }

    @Override
    public String toString() {
        return contenido.getNombre() + " (visto: " + fechaVisto +
                ", puntuación: " + (puntuacion == 0 ? "sin puntuar" : puntuacion + "⭐") + ")";
    }
}
