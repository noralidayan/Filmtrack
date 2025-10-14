package com.filmtrack.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Actor extends Persona {
    public String nombreArtistico;
    @ManyToMany(mappedBy = "reparto")
    private List<ContenidoAudiovisual> filmografia = new ArrayList<>();

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
