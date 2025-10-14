package com.filmtrack.controllers;

import com.filmtrack.models.ContenidoAudiovisual;
import com.filmtrack.models.Usuario;
import com.filmtrack.models.Visualizacion;
import com.filmtrack.persistence.ContenidoAudiovisualDAO;
import com.filmtrack.persistence.UsuarioDAO;
import com.filmtrack.persistence.VisualizacionDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private VisualizacionDAO visualizacionDAO;
    private ContenidoAudiovisualDAO contenidoDAO;

    public UsuarioController(UsuarioDAO usuarioDAO, VisualizacionDAO visualizacionDAO, ContenidoAudiovisualDAO contenidoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.visualizacionDAO = visualizacionDAO;
        this.contenidoDAO = contenidoDAO;
    }

    public boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }

    private boolean validarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Usuario no válido");
            return false;
        }
        return true;
    }

    public Usuario crearUsuario(String nombre, String email, String nombreUsuario, String clave, LocalDate fechaNacimiento) {if (!validarEmail(email)) {
        System.out.println("Formato de email incorrecto");
        return null;
    }

        if (usuarioDAO.buscarPorEmail(email) != null) {
            System.out.println("Ya existe un usuario con ese email");
            return null;
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setNombreUsuario(nombreUsuario);
        nuevo.setClave(BCrypt.hashpw(clave, BCrypt.gensalt()));
        nuevo.setFechaNacimiento(fechaNacimiento);
        nuevo.setFavoritos(new ArrayList<>());
        nuevo.setHistorialVistos(new ArrayList<>());

        usuarioDAO.guardar(nuevo);
        System.out.println("Usuario creado exitosamente: " + nombreUsuario);
        return nuevo;
    }

    public Usuario iniciarSesion(String email, String claveIngresada) {
        if (!validarEmail(email)) {
            System.out.println("Formato de email incorrecto");
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) {
            System.out.println("Email o contraseña incorrectos");
            return null;
        }

        if (BCrypt.checkpw(claveIngresada, usuario.getClave())) {
            System.out.println("Login exitoso");
            return usuario;
        } else {
            System.out.println("Email o contraseña incorrectos");
            return null;
        }
    }


    public void agregarFavorito(Usuario usu, ContenidoAudiovisual contenido) {
        if (!validarUsuario(usu)) return;

        if (contenido.getId() == 0) {
            contenidoDAO.guardar(contenido);
        }

        if (usu.getFavoritos() == null) usu.setFavoritos(new ArrayList<>());

        ContenidoAudiovisual contenidoDB = contenidoDAO.buscarPorId(contenido.getId());

        boolean existe = usu.getFavoritos().stream()
                .anyMatch(c -> c.getId() == contenidoDB.getId());

        if (!existe) {
            usu.getFavoritos().add(contenidoDB);
            System.out.println(contenidoDB.getNombre() + " añadido a favoritos exitosamente");
        } else {
            System.out.println(contenidoDB.getNombre() + " ya se encuentra en la lista");
        }

        usuarioDAO.actualizar(usu);
    }

    public void agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido, LocalDate fechaVisto) {
        if (!validarUsuario(usu)) return;

        if (contenido.getId() == 0) {
            contenidoDAO.guardar(contenido);
        }

        if (usu.getHistorialVistos() == null) usu.setHistorialVistos(new ArrayList<>());

        Visualizacion vis = new Visualizacion();
        vis.setUsuario(usu);
        vis.setContenido(contenido);
        vis.setFechaVisto(fechaVisto != null ? fechaVisto : LocalDate.now());

        // Persistimos la visualización
        visualizacionDAO.guardar(vis);

        // Agregamos al historial del usuario en memoria
        usu.getHistorialVistos().add(vis);

        System.out.println(contenido.getNombre() + " añadido al historial exitosamente");

        usuarioDAO.actualizar(usu);
    }
    public void obtenerFavoritos(Usuario usu) {
        if (!validarUsuario(usu)) return;

        List<ContenidoAudiovisual> favoritos = usu.getFavoritos();
        if (favoritos.isEmpty()) {
            System.out.println("Lista de favoritos vacía");
        } else {
            System.out.println("Lista de favoritos:");
            favoritos.forEach(c -> System.out.println(c.getNombre()));
        }
    }

    public void agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido) {
        if (!validarUsuario(usu)) return;

        if (contenido.getId() == 0) {
            contenidoDAO.guardar(contenido);
        }

        if (usu.getHistorialVistos() == null) usu.setHistorialVistos(new ArrayList<>());

        boolean existe = usu.getHistorialVistos().stream()
                .anyMatch(v -> v.getContenido().getId() == contenido.getId());

        if (!existe) {
            Visualizacion v = new Visualizacion();
            v.setUsuario(usu);
            v.setContenido(contenido);
            v.setFechaVisto(LocalDate.now());

            visualizacionDAO.guardar(v);

            usu.getHistorialVistos().add(v);

            System.out.println(contenido.getNombre() + " agregado al historial");
        } else {
            System.out.println(contenido.getNombre() + " ya se encuentra en el historial");
        }

        usuarioDAO.actualizar(usu);
    }


    public void obtenerHistorial(Usuario usu) {
        if (!validarUsuario(usu)) return;

        List<Visualizacion> historial = usuarioDAO.obtenerHistorialCompleto(usu.getId());
        if (historial.isEmpty()) {
            System.out.println("Historial vacío");
        } else {
            System.out.println("Historial de visualizaciones:");
            historial.forEach(v -> System.out.println(v.getContenido().getNombre() + " - Visto el: " + v.getFechaVisto()));
        }
    }


    public void puntuarContenido(Usuario usu, ContenidoAudiovisual contenido, int valor) {
        if (!validarUsuario(usu)) return;

        for (Visualizacion v : usu.getHistorialVistos()) {
            if (v.getContenido().equals(contenido)) {
                if (valor >= 1 && valor <= 5) {
                    v.setPuntuacion(valor);
                    contenido.setPuntuacionEnEstrellas(valor);
                    visualizacionDAO.guardar(v);
                    usuarioDAO.guardar(usu);
                    System.out.println("Le has otorgado " + valor + " estrellas a " + contenido.getNombre());
                } else {
                    System.out.println("Solo se aceptan valores del 1 al 5");
                }
                return;
            }
        }

        System.out.println("No podés puntuar " + contenido.getNombre() + " porque no está en tu historial.");
    }
}
