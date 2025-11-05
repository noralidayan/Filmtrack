package com.filmtrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 Clase que representa una visualización realizada por un usuario.
 Relaciona un Usuario con un ContenidoAudiovisual y almacena la fecha y puntuación asignada.
 Conceptos de POO presentes:
 - Asociación: une objetos de tipo Usuario y ContenidoAudiovisual.
 - Encapsulamiento: protege los atributos mediante getters y setters.
 - Instanciación: permite crear objetos que reflejan acciones reales del sistema (ver un contenido).
 */
@Entity
@Table(name = "visualizacion") // asegura que la tabla tenga un nombre explícito y único
public class Visualizacion {

    /** Identificador único autogenerado para cada visualización. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Indica si el registro está activo dentro del sistema. */
    @Column(nullable = false)
    private boolean activo = true;

    /** Puntuación asignada al contenido, de 1 a 5 estrellas (0 si no fue puntuada). */
    @Column(nullable = false)
    private int puntuacion = 0;

    /** Fecha en que el usuario vio el contenido. */
    @Column(name = "fechaVisto", nullable = false)
    private LocalDate fechaVisto;

    /**
     Asociación muchos a uno con ContenidoAudiovisual.
     Se muestra el contenido para permitir visualizar el nombre en el frontend.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "contenido_id", nullable = false)
    private ContenidoAudiovisual contenido;

    /**
     Asociación muchos a uno con Usuario.
     Se ignora en la serialización JSON para evitar bucles recursivos.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    public Visualizacion() {}

    /**
     Constructor que inicializa la visualización con los datos principales.
     Establece usuario, contenido y fecha, con valores por defecto para activo y puntuación.
     */
    public Visualizacion(Usuario usuario, ContenidoAudiovisual contenido, LocalDate fechaVisto) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaVisto = (fechaVisto != null) ? fechaVisto : LocalDate.now();
        this.activo = true;
        this.puntuacion = 0;
    }

    /**
     Devuelve una representación visual de la puntuación en estrellas.
     Es un método transitorio (no persistente en la base).
     */
    @Transient
    public String getEstrellas() {
        if (puntuacion <= 0) return "Sin puntuar";
        return "⭐".repeat(puntuacion);
    }

    // Getters y Setters
    public int getId() { return id; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getPuntuacion() { return puntuacion; }

    /**
     Setter con validación del rango de puntuación.
     Solo permite valores entre 0 y 5.
     */
    public void setPuntuacion(int puntuacion) {
        if (puntuacion < 0 || puntuacion > 5)
            throw new IllegalArgumentException("La puntuación debe estar entre 0 y 5.");
        this.puntuacion = puntuacion;
    }

    public LocalDate getFechaVisto() { return fechaVisto; }
    public void setFechaVisto(LocalDate fechaVisto) {
        this.fechaVisto = (fechaVisto != null) ? fechaVisto : LocalDate.now();
    }

    public ContenidoAudiovisual getContenido() { return contenido; }
    public void setContenido(ContenidoAudiovisual contenido) { this.contenido = contenido; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    /**
     Redefinición del metodo toString().
     Devuelve una descripción legible de la visualización con fecha y puntuación.
     */
    @Override
    public String toString() {
        return contenido.getNombre() + " (visto: " + fechaVisto +
                (puntuacion == 0 ? ", sin puntuar" : ", " + puntuacion + "⭐") + ")";
    }
}