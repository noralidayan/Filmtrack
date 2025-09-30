package com.filmtrack.entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//usuario hereda de Persona
public class Usuario extends Persona {
    private String nombreUsuario;
    private String email;
    private String clave;

    private List<ContenidoAudiovisual> favoritos = new ArrayList<>();
    private List<Visualizacion> historialVistos = new ArrayList<>();

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

    //se agrega a los métodos estructuras condicionales para validar la presencia de elementos en listas y mostrar alertas en pantalla
    public void agregarAlHistorial(Visualizacion contenidoVisto) {
        if (!historialVistos.contains(contenidoVisto)) {
            historialVistos.add(contenidoVisto);
            System.out.println(contenidoVisto + " Agregado exitosamente");
        } else {
            System.out.println(contenidoVisto + " ya se encuentra en la lista");
        }
    }

    public void obtenerHistorial() {
        if (historialVistos.isEmpty()) {
            System.out.println("Lista vacía");
        } else {
            System.out.println("Historial de visualizaciones:");
            for (Visualizacion contenidoVisto : historialVistos) {
                System.out.println(contenidoVisto);
            }
        }
    }

    public void obtenerFavoritos() {
        if (favoritos.isEmpty()) {
            System.out.println("Lista vacía");
        } else {
            System.out.println("Lista de favoritos:");
            for (ContenidoAudiovisual contenidoFavorito : favoritos) {
                System.out.println(contenidoFavorito.getNombre());
            }
        }
    }

    public void agregarFavorito(ContenidoAudiovisual contenido) {
        if (favoritos.contains(contenido)) {
            System.out.println(contenido + " ya se encuentra en la lista");
        } else {
            favoritos.add(contenido);
            System.out.println(contenido + " añadido a favoritos exitosamente");
        }
    }

    //se agrega metodo puntuar a clase usuario, se elimina de visualizacion
    public void puntuarContenido(ContenidoAudiovisual contenido, int valor) {
        for (Visualizacion v : historialVistos) {
            if (v.getContenido().equals(contenido)) {
                if (valor >= 1 && valor <= 5) {
                    v.setPuntuacion(valor);
                    contenido.setPuntuacionEnEstrellas(valor);
                    System.out.println("Le has otorgado " + valor + " estrellas a " + contenido.getNombre());
                } else {
                    System.out.println("Solo se aceptan valores del 1 al 5");
                }
                return;
            }
        }
        System.out.println("No podés puntuar " + contenido.getNombre() + " porque no está en tu historial.");
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