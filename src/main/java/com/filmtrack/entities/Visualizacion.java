package com.filmtrack.entities;

public class Visualizacion {
    private ContenidoAudiovisual contenido;
    private String fechaVisto;

    public Visualizacion(ContenidoAudiovisual contenido, String fechaVisto) {
        this.contenido = contenido;
        this.fechaVisto = fechaVisto;
    }

    public Visualizacion() {
    }

    public ContenidoAudiovisual getContenido() {
        return contenido;
    }

    public void setContenido(ContenidoAudiovisual contenido) {
        this.contenido = contenido;
    }

    public String getFechaVisto() {
        return fechaVisto;
    }

    public void setFechaVisto(String fechaVisto) {
        this.fechaVisto = fechaVisto;
    }

    public void puntuar(){}
}
