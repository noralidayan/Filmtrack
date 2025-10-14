package com.filmtrack.UI;

import com.filmtrack.controllers.UsuarioController;
import com.filmtrack.models.ContenidoAudiovisual;
import com.filmtrack.models.Usuario;
import com.filmtrack.models.Visualizacion;
import com.filmtrack.persistence.ContenidoAudiovisualDAO;
import com.filmtrack.persistence.UsuarioDAO;
import com.filmtrack.persistence.VisualizacionDAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PruebasInteractivo {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static VisualizacionDAO visualizacionDAO = new VisualizacionDAO();
    private static ContenidoAudiovisualDAO contenidoDAO = new ContenidoAudiovisualDAO();
    private static UsuarioController usuarioController = new UsuarioController(usuarioDAO, visualizacionDAO, contenidoDAO);
    private static Usuario usuLogueado;
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1- Crear usuario");
            System.out.println("2- Iniciar sesión");
            System.out.println("3- Salir");
            System.out.print("Opción: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1": crearUsuario(); break;
                case "2": loginUsuario(); if (usuLogueado != null) menuUsuario(); break;
                case "3": System.out.println("Saliendo..."); return;
                default: System.out.println("Opción inválida");
            }
        }
    }


    private static void crearUsuario() {
        try {
            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("Email: ");
            String email = teclado.nextLine();

            System.out.print("Nombre de usuario: ");
            String nombreUsuario = teclado.nextLine();

            System.out.print("Clave: ");
            String clave = teclado.nextLine();

            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String fechaStr = teclado.nextLine();
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Llamamos al controller, que hace todo el resto
            Usuario nuevo = usuarioController.crearUsuario(nombre, email, nombreUsuario, clave, fecha);
            if (nuevo != null) usuLogueado = nuevo; // loguea automáticamente
        } catch (Exception e) {
            System.out.println("Error al crear usuario. Revisa los datos ingresados.");
            e.printStackTrace();
        }
    }


    private static void loginUsuario() {
        System.out.print("Email: ");
        String email = teclado.nextLine();

        System.out.print("Clave: ");
        String clave = teclado.nextLine();

        usuLogueado = usuarioController.iniciarSesion(email, clave);
        if (usuLogueado != null) {
            System.out.println("¡Bienvenida, " + usuLogueado.getNombreUsuario() + "!");
        }
    }

    private static void menuUsuario() {
        while (true) {
            System.out.println("\n--- Menú Usuario ---");
            System.out.println("1- Agregar favorito");
            System.out.println("2- Agregar al historial");
            System.out.println("3- Ver favoritos");
            System.out.println("4- Ver historial");
            System.out.println("5- Cerrar sesión");
            System.out.print("Opción: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Nombre del contenido a agregar a favoritos: ");
                    String nombreFav = teclado.nextLine();
                    ContenidoAudiovisual contenidoFav = new ContenidoAudiovisual(nombreFav);
                    usuarioController.agregarFavorito(usuLogueado, contenidoFav);
                    break;

                case "2":
                    System.out.print("Nombre del contenido visto: ");
                    String nombreHist = teclado.nextLine();
                    ContenidoAudiovisual contenidoHist = new ContenidoAudiovisual(nombreHist);

                    System.out.print("Fecha de visualización (dd/MM/yyyy, ENTER para hoy): ");
                    String fechaInput = teclado.nextLine();
                    LocalDate fecha = fechaInput.isEmpty() ?
                            LocalDate.now() :
                            LocalDate.parse(fechaInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    usuarioController.agregarAlHistorial(usuLogueado, contenidoHist);
                    break;

                case "3":
                    usuarioController.obtenerFavoritos(usuLogueado);
                    break;

                case "4":
                    usuarioController.obtenerHistorial(usuLogueado);
                    break;

                case "5":
                    usuLogueado = null;
                    System.out.println("Sesión cerrada");
                    return;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}
