package com.filmtrack.logica.models;

import com.filmtrack.entities.ContenidoAudiovisual;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Actor extends Persona {
    public String nombreArtistico;
    public List<ContenidoAudiovisual> filmografia;

    public Actor() {
    }


    public Actor(String nombre, String apellido, LocalDate fechaNacimiento, String nombreArtistico, List<ContenidoAudiovisual> filmografia) {
        super(nombre, apellido, fechaNacimiento);
        this.nombreArtistico = nombreArtistico;
        this.filmografia = filmografia;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public List<ContenidoAudiovisual> getFilmografia() {
        return filmografia;
    }

    public void setFilmografia(List<ContenidoAudiovisual> filmografia) {
        this.filmografia = filmografia;
    }


}
