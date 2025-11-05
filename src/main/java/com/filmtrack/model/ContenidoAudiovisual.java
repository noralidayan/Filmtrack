package com.filmtrack.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
  Clase base que representa cualquier tipo de contenido audiovisual del sistema.
  Es una entidad general de la que heredan Película y Serie.
  Se aplican varios conceptos de POO:
  - Abstracción: define las características comunes a todos los contenidos, significativas para el sistema.
  - Herencia: permite que las subclases especialicen sus propios atributos y comportamientos.
  - Encapsulamiento: los atributos están protegidos y se accede a ellos mediante getters y setters.
 */
@Entity
@Table(name = "contenidoaudiovisual")
@Inheritance(strategy = InheritanceType.JOINED)
public class ContenidoAudiovisual {

    @Id //Identificador único autogenerado para cada contenido
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String genero;
    private String fechaLanzamiento;
    /** Campo de control de versiones para manejar actualizaciones concurrentes. */
    @Version
    private int version;
    /** Indica si el contenido está activa dentro del sistema/DB. */
    private boolean activo = true;
    private int puntuacionEnEstrellas;

    /**
      Relación muchos a muchos entre contenido y actores.
      Un mismo actor puede participar en varios contenidos y viceversa.
     */
    @ManyToMany
    @JoinTable(
            name = "contenido_reparto",
            joinColumns = @JoinColumn(name = "contenido_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> reparto = new ArrayList<>();

    /**
      Constructores con diferentes parámetros - Demuestra sobrecarga de constructores (polimorfismo).
     */
    public ContenidoAudiovisual() {}

    public ContenidoAudiovisual(String nombre) {
        this.nombre = nombre;
    }

    public ContenidoAudiovisual(String nombre, String fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.puntuacionEnEstrellas = puntuacionEnEstrellas;
        this.genero = genero != null ? genero : "Desconocido";
        this.reparto = reparto != null ? reparto : new ArrayList<>();
        this.activo = true;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getPuntuacionEnEstrellas() {
        return puntuacionEnEstrellas;
    }

    /**
      Setter con validación de rango.
      Aplica encapsulamiento para controlar que la puntuación sea válida.
     */
    public void setPuntuacionEnEstrellas(int puntuacionEnEstrellas) {
        if (puntuacionEnEstrellas < 0 || puntuacionEnEstrellas > 5)
            throw new IllegalArgumentException("La puntuación debe estar entre 0 y 5");

        this.puntuacionEnEstrellas = puntuacionEnEstrellas;
    }

    public List<Actor> getReparto() {
        return reparto;
    }

    public void setReparto(List<Actor> reparto) {
        this.reparto = reparto;
    }

    /**
      toString -> Metodo que devuelve una representación legible del contenido.
      Es una forma de polimorfismo, ya que cada subclase puede redefinirlo.
     */
    @Override
    public String toString() {
        return nombre + " (" + (puntuacionEnEstrellas == 0 ? "sin puntuar" : puntuacionEnEstrellas + "⭐") + ")";
    }
}