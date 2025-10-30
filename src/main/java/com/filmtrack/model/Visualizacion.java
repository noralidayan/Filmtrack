package com.filmtrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Visualizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean activo = true;
    private int puntuacion = 0;
    private LocalDate fechaVisto;

    @ManyToOne
    @JoinColumn(name = "contenido_id")
    private ContenidoAudiovisual contenido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Visualizacion() {}

    public Visualizacion(Usuario usuario, ContenidoAudiovisual contenido, LocalDate fechaVisto) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaVisto = fechaVisto;
        this.activo = true;
        this.puntuacion = 0;
    }

    // Getters y Setters
    public int getId() { return id; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) {
        if (puntuacion >= 1 && puntuacion <= 5) {
            this.puntuacion = puntuacion;
        } else {
            System.out.println("La puntuación debe estar entre 1 y 5.");
        }
    }

    public LocalDate getFechaVisto() { return fechaVisto; }
    public void setFechaVisto(LocalDate fechaVisto) { this.fechaVisto = fechaVisto; }

    public ContenidoAudiovisual getContenido() { return contenido; }
    public void setContenido(ContenidoAudiovisual contenido) { this.contenido = contenido; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public String toString() {
        return contenido.getNombre() + " (visto: " + fechaVisto +
                (puntuacion == 0 ? ", sin puntuar" : ", " + puntuacion + "⭐") + ")";
    }
}