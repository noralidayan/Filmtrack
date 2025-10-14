package com.filmtrack.persistence;

import java.sql.*;

public class MySqlConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/filmtrack?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";

        String sql = "SELECT id, nombre, fechaLanzamiento, genero, puntuacionEnEstrellas, tipo " +
                "FROM contenidoaudiovisual WHERE TIPO= 'pelicula'";

        try (Connection connect = DriverManager.getConnection(url, username, password);
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String fecha = rs.getString("fechaLanzamiento");
                String genero = rs.getString("genero");
                int estrellas = rs.getInt("puntuacionEnEstrellas");
                String tipo = rs.getString("tipo");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Fecha: " + fecha +
                        ", Género: " + genero + ", Estrellas: " + estrellas +
                        ", Tipo: " + tipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
