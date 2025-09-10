package com.filmtrack.entities;
import java.util.List;

public class Usuario extends Persona{
    private String nombreUsuario;
    private String email;
    private String clave;

    private List<ContenidoAudiovisual> favoritos;
    private List <Visualizacion> historialVistos;

    public Usuario(String nombreUsuario, String email, String clave, List<ContenidoAudiovisual> favoritos, List<Visualizacion> historialVistos) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.clave = clave;
        this.favoritos = favoritos;
        this.historialVistos = historialVistos;
    }

    public Usuario() {
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

    public void agregarAlHistorial(){}

    public void obtenerHistorial(){}

    public void agregarFavorito(){}

}
