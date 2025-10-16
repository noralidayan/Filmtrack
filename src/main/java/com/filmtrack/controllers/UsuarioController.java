package com.filmtrack.controllers;

import com.filmtrack.Services.UsuarioService;
import com.filmtrack.models.Usuario;
import com.filmtrack.models.ContenidoAudiovisual;
import com.filmtrack.models.Visualizacion;

import java.time.LocalDate;
import java.util.List;

public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario registrarUsuario(String nombre, String email, String nombreUsuario, String clave, LocalDate fechaNacimiento) {
        Usuario nuevo = usuarioService.crearUsuario(nombre, email, nombreUsuario, clave, fechaNacimiento);
        if (nuevo != null) {
            System.out.println("Usuario creado exitosamente: " + nombreUsuario);
        } else {
            System.out.println("Error al crear usuario. Email inválido o ya registrado.");
        }
        return nuevo;
    }

    public Usuario login(String email, String clave) {
        Usuario usuario = usuarioService.iniciarSesion(email, clave);
        if (usuario != null) {
            System.out.println("Login exitoso: " + usuario.getNombreUsuario());
        } else {
            System.out.println("Email o contraseña incorrectos.");
        }
        return usuario;
    }

    public void agregarFavoritos(Usuario usu, String nombreContenido) {
        boolean agregado = usuarioService.agregarFavorito(usu, nombreContenido);
        if (agregado) {
            System.out.println(nombreContenido + " agregado a favoritos.");
        } else {
            System.out.println(nombreContenido + " ya estaba en favoritos.");
        }
    }


    public void mostrarFavoritos(Usuario usu) {
        List<ContenidoAudiovisual> favoritos = usuarioService.obtenerFavoritos(usu);
        if (favoritos.isEmpty()) {
            System.out.println("No tienes contenidos favoritos.");
        } else {
            System.out.println("Favoritos:");
            favoritos.forEach(c -> System.out.println("- " + c.getNombre()));
        }
    }

    public void agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido) {
        boolean agregado = usuarioService.agregarAlHistorial(usu, contenido);
        if (agregado) {
            System.out.println(contenido.getNombre() + " agregado al historial.");
        } else {
            System.out.println(contenido.getNombre() + " ya estaba en el historial.");
        }
    }

    public void mostrarHistorial(Usuario usu) {
        List<Visualizacion> historial = usuarioService.obtenerHistorial(usu);
        if (historial.isEmpty()) {
            System.out.println("Historial vacío.");
        } else {
            System.out.println("Historial de visualizaciones:");
            historial.forEach(v -> System.out.println(v.getContenido().getNombre() + " - Visto el: " + v.getFechaVisto()));
        }
    }

    public void puntuarContenido(Usuario usu, ContenidoAudiovisual contenido, int valor) {
        boolean exito = usuarioService.puntuarContenido(usu, contenido, valor);
        if (exito) {
            System.out.println("Le has otorgado " + valor + " estrellas a " + contenido.getNombre());
        } else {
            System.out.println("No se pudo puntuar " + contenido.getNombre() + ". Verifica si el contenido está en tu historial o si el puntaje es válido.");
        }
    }




}
