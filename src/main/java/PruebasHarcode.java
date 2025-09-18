import com.filmtrack.entities.ContenidoAudiovisual;
import com.filmtrack.entities.Usuario;
import com.filmtrack.entities.Visualizacion;

public class PruebasHarcode {
    public static void main(String[] args) {
        Usuario usu1 = new Usuario();
        ContenidoAudiovisual peli1 = new ContenidoAudiovisual();
        peli1.setNombre("El conjuro");
        ContenidoAudiovisual peli2 = new ContenidoAudiovisual();
        peli2.setNombre("Harry Potter");
        ContenidoAudiovisual serie1 = new ContenidoAudiovisual();
        serie1.setNombre("Twin Peeaks");
        usu1.setNombreUsuario("Luz");
        usu1.agregarFavorito(peli2);
        usu1.agregarFavorito(peli1);
        usu1.agregarFavorito(serie1);
        System.out.println("Favoritos de " + usu1.getNombreUsuario() + ":");
        for (ContenidoAudiovisual c : usu1.getFavoritos()) {
            System.out.println(c.getNombre());
        }
        Visualizacion v1 = new Visualizacion(peli1, "15/09/2025"); // fecha opcional
        usu1.agregarAlHistorial(v1);
        for (Visualizacion v : usu1.getHistorialVistos()) {
            System.out.println("Historial de visualizacion: " + v);
        }
    }
}
