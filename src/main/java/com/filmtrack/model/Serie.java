package com.filmtrack.model;

import jakarta.persistence.Entity;
import java.util.List;

/**
 Clase que representa una Serie dentro del sistema.
 Extiende de ContenidoAudiovisual, aplicando herencia y especialización.
 Conceptos de POO aplicados:
 - Herencia: la clase Serie hereda atributos y comportamientos de ContenidoAudiovisual.
 - Polimorfismo: redefine el método toString() para personalizar la representación del objeto.
 - Encapsulamiento: los atributos están protegidos y se accede a ellos mediante getters y setters.
 */
@Entity
public class Serie extends ContenidoAudiovisual {

    private int temporadas;
    private int capitulosPorTemporada;

    public Serie() {
        super();
    }

    /**
     Constructor completo que recibe los datos principales para crear una serie.
     Llama al constructor de la clase padre (super) y agrega los atributos específicos.
     */
    public Serie(String nombre, String fechaLanzamiento, int puntuacionEnEstrellas, String genero, List<Actor> reparto,
                 int temporadas, int capitulosPorTemporada) {
        super(nombre, fechaLanzamiento, puntuacionEnEstrellas, genero, reparto);
        this.temporadas = temporadas;
        this.capitulosPorTemporada = capitulosPorTemporada;
    }

    // Getters y Setters
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
        return getNombre() + " - " + temporadas + " temporadas, " +
                capitulosPorTemporada + " capítulos por temporada (" +
                (getPuntuacionEnEstrellas() == 0 ? "sin puntuar" : getPuntuacionEnEstrellas() + "⭐") + ")";
    }
}