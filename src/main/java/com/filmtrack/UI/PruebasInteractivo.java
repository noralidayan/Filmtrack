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
import java.util.List;
import java.util.Scanner;

public class PruebasInteractivo {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static VisualizacionDAO visualizacionDAO = new VisualizacionDAO();
    private static ContenidoAudiovisualDAO contenidoDAO = new ContenidoAudiovisualDAO();
    private static UsuarioController usuarioController = new UsuarioController(
            new com.filmtrack.Services.UsuarioService(usuarioDAO, visualizacionDAO, contenidoDAO)
    );
    private static Usuario usuLogueado;
    private static Scanner teclado = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
            LocalDate fecha = LocalDate.parse(teclado.nextLine(), formatter);

            usuLogueado = usuarioController.registrarUsuario(nombre, email, nombreUsuario, clave, fecha);
        } catch (Exception e) {
            System.out.println("Error al crear usuario. Revisa los datos ingresados.");
        }
    }

    private static void loginUsuario() {
        System.out.print("Email: ");
        String email = teclado.nextLine();
        System.out.print("Clave: ");
        String clave = teclado.nextLine();
        usuLogueado = usuarioController.login(email, clave);
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
                    usuarioController.agregarFavoritos(usuLogueado, nombreFav);
                    break;

                case "2":
                    System.out.print("Nombre del contenido visto: ");
                    String nombreHist = teclado.nextLine();
                    ContenidoAudiovisual contenidoHist = contenidoDAO.buscarPorNombre(nombreHist);

                    if (contenidoHist == null) {
                        contenidoHist = new ContenidoAudiovisual(nombreHist);

                        // Pedir puntuación al crear el contenido nuevo
                        int estrellas = 0;
                        while (true) {
                            try {
                                System.out.print("Puntuación inicial del contenido (1-5, ENTER para 0): ");
                                String input = teclado.nextLine();
                                if (input.isEmpty()) {
                                    estrellas = 0;
                                } else {
                                    estrellas = Integer.parseInt(input);
                                    if (estrellas < 0 || estrellas > 5) throw new NumberFormatException();
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ingrese un número válido entre 0 y 5.");
                            }
                        }

                        contenidoHist.setPuntuacionEnEstrellas(estrellas);
                        contenidoDAO.guardar(contenidoHist);
                    }

                    System.out.print("Fecha de visualización (dd/MM/yyyy, ENTER para hoy): ");
                    String fechaInput = teclado.nextLine();
                    LocalDate fecha = fechaInput.isEmpty() ? LocalDate.now() : LocalDate.parse(fechaInput, formatter);

                    // Crear visualización con la fecha
                    Visualizacion vis = new Visualizacion(usuLogueado, contenidoHist, fecha);
                    visualizacionDAO.guardar(vis);

                    System.out.println("Contenido agregado al historial correctamente.");
                    break;



                case "3":
                    usuarioController.mostrarFavoritos(usuLogueado);
                    break;

                case "4":
                    usuarioController.mostrarHistorial(usuLogueado);
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
