package com.filmtrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
  Clase que representa a un Usuario dentro del sistema.
  Hereda de Persona, aplicando herencia y agregando atributos y relaciones propias.
 */
@Entity
public class Usuario extends Persona {

    private String nombreUsuario;
    private String email;
    private String clave;
    /**
      Relación muchos a muchos con ContenidoAudiovisual.
      Representa los contenidos marcados como favoritos por el usuario.
     */
    @ManyToMany
    @JoinTable(
            name = "usuario_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "contenido_id")
    )
    private List<ContenidoAudiovisual> favoritos = new ArrayList<>();
    /**
      Relación uno a muchos con Visualizacion.
      Representa el historial de contenidos vistos por el usuario.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visualizacion> historialVistos = new ArrayList<>();

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String apellido, LocalDate fechaNacimiento,
                   String nombreUsuario, String email, String clave) {
        super(nombre, apellido, fechaNacimiento);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.clave = clave;
        this.favoritos = new ArrayList<>();
        this.historialVistos = new ArrayList<>();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<ContenidoAudiovisual> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<ContenidoAudiovisual> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Visualizacion> getHistorialVistos() {
        return historialVistos;
    }

    public void setHistorialVistos(List<Visualizacion> historialVistos) {
        this.historialVistos = historialVistos;
    }

    /**
      Redefinición del metodo toString().
      Muestra información básica del usuario sin detallar relaciones completas.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}