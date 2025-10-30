package com.filmtrack.controller;

import com.filmtrack.model.ContenidoAudiovisual;

public class ContenidoAudiovisualController {

    public void mostrarInfo(ContenidoAudiovisual contenido) {
        if (contenido == null) {
            System.out.println("No hay contenido para mostrar.");
            return;
        }

        System.out.println("Nombre: " + contenido.getNombre());
        System.out.println("Género: " + contenido.getGenero());
        System.out.println("Puntuación: " +
                (contenido.getPuntuacionEnEstrellas() == 0 ? "sin puntuar" : contenido.getPuntuacionEnEstrellas() + "⭐"));
    }
}
