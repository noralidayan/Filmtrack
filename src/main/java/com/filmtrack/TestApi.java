package com.filmtrack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.*;
import java.net.URI;

public class TestApi {
    public static void main(String[] args) throws Exception {
        int userId = 11;
        String userName = "Lua";

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // ----- FAVORITOS -----
        HttpRequest favRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/usuarios/" + userId + "/favoritos"))
                .GET()
                .build();

        HttpResponse<String> favResponse = client.send(favRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode favoritos = mapper.readTree(favResponse.body());

        System.out.println("========== FAVORITOS del usuario " + userName + " ==========");
        for (JsonNode peli : favoritos) {
            System.out.println("Nombre: " + peli.get("nombre").asText());
            System.out.println("Género: " + peli.get("genero").asText());
            System.out.println("Puntuación (contenido): " +
                    (peli.has("puntuacionEnEstrellas") ? peli.get("puntuacionEnEstrellas").asInt() : "sin puntuar"));
            System.out.println("-----------------------------------");
        }

        // ----- HISTORIAL -----
        HttpRequest histRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/usuarios/" + userId + "/historial"))
                .GET()
                .build();

        HttpResponse<String> histResponse = client.send(histRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode historial = mapper.readTree(histResponse.body());

        System.out.println("\n========== HISTORIAL de visualizaciones ==========");
        for (JsonNode v : historial) {
            JsonNode contenido = v.get("contenido");
            System.out.println("Contenido: " + (contenido != null ? contenido.get("nombre").asText() : "desconocido"));
            System.out.println("Fecha vista: " + (v.has("fechaVisto") ? v.get("fechaVisto").asText() : "no registrada"));
            System.out.println("Puntuación: " + (v.has("puntuacion") ? v.get("puntuacion").asInt() : 0) + "⭐");
            System.out.println("-----------------------------------");
        }
    }
}
