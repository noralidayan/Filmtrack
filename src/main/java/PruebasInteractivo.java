import com.filmtrack.entities.ContenidoAudiovisual;
import com.filmtrack.entities.Usuario;
import com.filmtrack.entities.Visualizacion;
import com.sun.source.tree.WhileLoopTree;

import java.sql.SQLOutput;
import java.util.Scanner;

public class PruebasInteractivo {
    private static Usuario usu1; // ahora es visible en toda la clase
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Ingresa tu nombre de usuario");
        String nombreUsuario = teclado.nextLine();
        usu1 = new Usuario();
        usu1.setNombreUsuario(nombreUsuario);
        System.out.println("Bienvendo/a " + nombreUsuario);
        mostrarMenu();


       /* String titulo = teclado.nextLine();

        for (Visualizacion v : usu1.getHistorialVistos()) {
            if (v.getContenido().getNombre().equalsIgnoreCase(titulo)) {
                usu1.puntuarContenido(v.getContenido(), 0); // 0 porque dentro se pide valor con Scanner
                break;
            }
        }*/
    }

    public static void mostrarMenu() {
        while (true) {
            System.out.println("Que acción deseas realizar? (elige del 1 - 5)");
            System.out.println("1- Agregar título a favoritos");
            System.out.println("2- Agregar título a Historial de visualizaciones");
            System.out.println("3- Consultar lista de favoritos");
            System.out.println("4- Consultar historial");
            System.out.println("5- Salir");
            System.out.println("");
            String opcion = teclado.nextLine();
            switch (opcion) {
                case "1":
                    System.out.print("Ingrese el nombre del título a agregar a favoritos: ");
                    String tituloFav = teclado.nextLine();
                    ContenidoAudiovisual nuevo = new ContenidoAudiovisual();
                    nuevo.setNombre(tituloFav);
                    usu1.agregarFavorito(nuevo);
                    break;

                case "2":
                    System.out.print("Ingrese el nombre del título visto: ");
                    String tituloHist = teclado.nextLine();
                    ContenidoAudiovisual visto = new ContenidoAudiovisual();
                    visto.setNombre(tituloHist);
                    System.out.println("Ingresa la fecha de visualización");
                    String fecha = teclado.nextLine();
                    Visualizacion vistoNuevo = new Visualizacion(visto, fecha);
                    vistoNuevo.setFechaVisto(fecha);
                    usu1.agregarAlHistorial(vistoNuevo);
                    break;

                case "3":
                    System.out.println("Favoritos:");
                    usu1.obtenerFavoritos();
                    break;

                case "4":
                    System.out.println("Historial:");
                    usu1.obtenerHistorial();
                    break;

                case "5":
                    System.out.println("Saliendo del programa.");
                    return;
                default:
                    System.out.println("Opción inválida, ingresa una opcion del 1 - 5");
            }
        }
    }
}