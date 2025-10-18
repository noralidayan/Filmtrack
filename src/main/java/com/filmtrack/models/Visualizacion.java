package com.filmtrack.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String version;
    private boolean activo;
    @ManyToOne
    @JoinColumn(name = "contenido_id")
    private ContenidoAudiovisual contenido;

    private LocalDate fechaVisto;
    private int puntuacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Visualizacion() {
    }

    public Visualizacion(Usuario usuario, ContenidoAudiovisual contenido, LocalDate fechaVisto) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaVisto = fechaVisto;
        this.puntuacion = 0;
        this.activo = true;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return contenido.getNombre() + " (visto: " + fechaVisto + (puntuacion == 0 ? "sin puntuar" : puntuacion + "⭐") + ")";
    }

    public void setUsuario(Usuario usu) {
        this.usuario = usu;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}