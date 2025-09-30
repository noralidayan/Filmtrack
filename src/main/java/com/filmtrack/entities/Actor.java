package com.filmtrack.entities;

import java.util.List;

public class Actor extends Persona{
    public String nombreArtistico;
    public List<ContenidoAudiovisual> filmografia;

    public Actor() {
    }


    public Actor(String nombre, String apellido, String fechaNacimiento, String nombreArtistico, List<ContenidoAudiovisual> filmografia) {
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
