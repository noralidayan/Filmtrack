package com.filmtrack.entities;

import java.util.ArrayList;
import java.util.List;

public class ContenidoAudiovisual {
    private String nombre;
    private String fechaLanzamiento;
    private int puntuacionEnEstrellas;
    private String genero;
    private List<Actor> reparto;

    public ContenidoAudiovisual(String nombre, String fechaLanzamiento,
                                int puntuacionEnEstrellas, String genero,
                                List<Actor> reparto) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.puntuacionEnEstrellas = puntuacionEnEstrellas;
        this.genero = genero;
       // if(reparto != null) {
            this.reparto = reparto;
    }

    public ContenidoAudiovisual() {
        // Constructor vacío
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public double getPuntuacionEnEstrellas() {
        return puntuacionEnEstrellas;
    }

    public void setPuntuacionEnEstrellas(int puntuacionEnEstrellas) {
        this.puntuacionEnEstrellas = puntuacionEnEstrellas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Actor> getReparto() {
        return reparto;
    }

    public void setReparto(List<Actor> reparto) {
        if(reparto != null) {
            this.reparto = reparto;
        }
    }

    public void mostrarInfo() {

    }
}