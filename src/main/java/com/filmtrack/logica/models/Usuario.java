package com.filmtrack.logica.models;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Basic
    private String nombreUsuario;
    @Basic
    private String email;
    private String version;
    private boolean activo;

    private String clave;

    private List<ContenidoAudiovisual> favoritos = new ArrayList<>();
    private List<Visualizacion> historialVistos = new ArrayList<>();

    public Usuario(int id, String version, boolean activo) {
        this.id = id;
        this.version = version;
        this.activo = activo;
    }

    //constructores, getter setter y metodos
    public Usuario(String nombreUsuario, String email, String clave, List<ContenidoAudiovisual> favoritos, List<Visualizacion> historialVistos) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.clave = clave;
        this.favoritos = favoritos;
        this.historialVistos = historialVistos;
    }

    public Usuario() {
    }

    public Usuario(String email, String clave) {
        this.email = email;
        this.clave = clave;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                ", favoritos=" + favoritos +
                ", historialVistos=" + historialVistos +
                '}';
    }
}