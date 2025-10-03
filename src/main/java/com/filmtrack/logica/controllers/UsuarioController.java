package com.filmtrack.logica.controllers;

import com.filmtrack.logica.models.ContenidoAudiovisual;
import com.filmtrack.logica.models.Usuario;
import com.filmtrack.logica.models.Visualizacion;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;

public class UsuarioController {
    private EntityManager em;

    public UsuarioController(EntityManager em) {
        this.em = em;
    }

    public void validarUsuario (Usuario usu){
        if (!usu.isActivo()) {
            System.out.println("Usuario inactivo.");
            return;
        }
    }
    public void  altaUsuario(Usuario usu){
        if (usu.getFavoritos() ==null) usu.setFavoritos(new ArrayList<>());
        if (usu.getHistorialVistos()== null) usu.setHistorialVistos(new ArrayList<>());
        usu.setActivo(true);
        try {
            em.getTransaction().begin();
            em.persist(usu);
            em.getTransaction().commit();
            System.out.println("Usuario dado de alta: " + usu.getNombreUsuario());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al dar de alta usuario: " + e.getMessage());
        }
    }

    public void bajaUsuario(Usuario usu){
        validarUsuario(usu);
        try {
            em.getTransaction().begin();
            usu.setActivo(false);
            em.merge(usu);
            em.getTransaction().commit();
            System.out.println("Usuario " + usu.getNombreUsuario() + " dado de baja (inactivo).");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al dar de baja usuario: " + e.getMessage());
        }
    }

        public void modificarUsuario(Usuario usu, String nuevoNombre, String nuevaClave) {
            validarUsuario(usu);

            if (nuevoNombre != null && !nuevoNombre.isEmpty()) usu.setNombreUsuario(nuevoNombre);
            if (nuevaClave != null && !nuevaClave.isEmpty()) usu.setClave(nuevaClave);

            try {
                em.getTransaction().begin();
                em.merge(usu);
                em.getTransaction().commit();
                System.out.println("Usuario modificado correctamente.");
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.out.println("Error al modificar usuario: " + e.getMessage());
            }
        }

    public Usuario login(String email, String clave) {
        try {
            Usuario usu = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email AND u.clave = :clave AND u.activo = true",
                            Usuario.class)
                    .setParameter("email", email)
                    .setParameter("clave", clave)
                    .getSingleResult();

            System.out.println("Login exitoso: " + usu.getNombreUsuario());
            return usu;
        } catch (jakarta.persistence.NoResultException e) {
            System.out.println("Email o contraseña incorrectos, o usuario inactivo.");
            return null;
        } catch (Exception e) {
            System.out.println("Error al intentar iniciar sesión: " + e.getMessage());
            return null;
        }
    }

    public void agregarAlHistorial(Usuario usu, Visualizacion contenidoVisto) {
        validarUsuario(usu);
        if (usu.getHistorialVistos() == null) usu.setHistorialVistos(new ArrayList<>());
        if (!usu.getHistorialVistos().contains(contenidoVisto)) {
            usu.getHistorialVistos().add(contenidoVisto);
            System.out.println(contenidoVisto + " agregado exitosamente");
        } else {
            System.out.println(contenidoVisto + " ya se encuentra en la lista");
        }
        em.getTransaction().begin();
        em.merge(usu);
        em.getTransaction().commit();
    }


    public void obtenerHistorial(Usuario usu) {
        validarUsuario(usu);
        if (usu.getHistorialVistos().isEmpty()) {
            System.out.println("Lista vacía");
        } else {
            System.out.println("Historial de visualizaciones:");
            for (Visualizacion contenidoVisto : usu.getHistorialVistos()) {
                System.out.println(contenidoVisto);
            }
        }
    }

    public void obtenerFavoritos(Usuario usu) {
        validarUsuario(usu);
        if (usu.getFavoritos().isEmpty()) {
            System.out.println("Lista vacía");
        } else {
            System.out.println("Lista de favoritos:");
            for (ContenidoAudiovisual contenidoFavorito : usu.getFavoritos()) {
                System.out.println(contenidoFavorito.getNombre());
            }
        }
    }

    public void agregarFavorito(Usuario usu, ContenidoAudiovisual contenido) {
        validarUsuario(usu);
        if (usu.getFavoritos() == null) usu.setFavoritos(new ArrayList<>());
        if (usu.getFavoritos().contains(contenido)) {
            System.out.println(contenido + " ya se encuentra en la lista");
        } else {
            usu.getFavoritos().add(contenido);
            System.out.println(contenido + " añadido a favoritos exitosamente");
        }
        em.getTransaction().begin();
        em.merge(usu);
        em.getTransaction().commit();
    }

    public void puntuarContenido(Usuario usu, ContenidoAudiovisual contenido, int valor) {
        validarUsuario(usu);
        for (Visualizacion v : usu.getHistorialVistos()) {
            if (v.getContenido().equals(contenido)) {
                if (valor >= 1 && valor <= 5) {
                    v.setPuntuacion(valor);
                    contenido.setPuntuacionEnEstrellas(valor);
                    System.out.println("Le has otorgado " + valor + " estrellas a " + contenido.getNombre());
                } else {
                    System.out.println("Solo se aceptan valores del 1 al 5");
                }
                try {
                    em.getTransaction().begin();
                    em.merge(usu);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                }
                return;
            }
        }
        System.out.println("No podés puntuar " + contenido.getNombre() + " porque no está en tu historial.");
    }
}