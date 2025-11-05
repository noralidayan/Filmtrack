package com.filmtrack.model;

import jakarta.persistence.Entity;
import java.util.List;

/**
 Clase que representa una Película dentro del sistema.
 Extiende de ContenidoAudiovisual, aplicando herencia para reutilizar atributos comunes.
 Conceptos de POO aplicados:
 - Herencia: Pelicula hereda de ContenidoAudiovisual, reutilizando sus atributos y métodos.
 - Polimorfismo: redefine toString() para mostrar información específica de películas.
 - Encapsulamiento: los atributos están protegidos y se accede mediante getters y setters.
 */
@Entity
public class Pelicula extends ContenidoAudiovisual {

    private int duracionEnMinutos;

    public Pelicula() {
        super();
    }

    /**
     Constructor completo que recibe todos los datos necesarios para crear una película.
     Utiliza el constructor de la clase padre (super) y agrega la duración específica de este tipo de contenido.
     */
    public Pelicula(String nombre, String fechaLanzamiento, int puntuacionEnEstrellas, String genero,
                    List<Actor> reparto, int duracionEnMinutos) {
        super(nombre, fechaLanzamiento, puntuacionEnEstrellas, genero, reparto);
        this.duracionEnMinutos = duracionEnMinutos;
    }

    // Getters y Setters
    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + duracionEnMinutos + " min) - " +
                (getPuntuacionEnEstrellas() == 0 ? "sin puntuar" : getPuntuacionEnEstrellas() + "⭐");
    }
}
