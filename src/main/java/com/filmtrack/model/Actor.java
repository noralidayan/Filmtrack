package com.filmtrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
  Clase que representa a un Actor dentro del sistema.
  Hereda de Persona, aplicando herencia y especialización.
  Además, se relaciona con los contenidos audiovisuales en los que participa.
 */
@Entity
public class Actor extends Persona {

    private String nombreArtistico; //Difiere del nombre heredado de persona
    /**
      Asociación bidireccional con ContenidoAudiovisual (ManyToMany).
      Refleja la participación de actores en distintas películas o series.
     */
    @ManyToMany(mappedBy = "reparto")
    private List<ContenidoAudiovisual> filmografia = new ArrayList<>();

    public Actor() {
        super();
    }
    /**
      Constructor completo que inicializa los datos principales del actor.
      Llama al constructor de la clase padre (super) para reutilizar los atributos heredados.
     */
    public Actor(String nombre, String apellido, LocalDate fechaNacimiento,
                 String nombreArtistico, List<ContenidoAudiovisual> filmografia) {
        super(nombre, apellido, fechaNacimiento);
        this.nombreArtistico = nombreArtistico;
        this.filmografia = filmografia != null ? filmografia : new ArrayList<>();
    }
    // Getters y Setters
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