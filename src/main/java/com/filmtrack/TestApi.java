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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/usuarios/" + userId + "/favoritos"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode favoritos = mapper.readTree(response.body());

        System.out.println("Favoritos del usuario " + userName + ":");
        for (JsonNode peli : favoritos) {
            System.out.println("Nombre: " + peli.get("nombre").asText());
            System.out.println("Género: " + peli.get("genero").asText());
            System.out.println("Puntuación: " + peli.get("puntuacionEnEstrellas").asInt());
            System.out.println("--------------");
        }
    }
}