package com.filmtrack.Services;

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

public class UsuarioService {
    private UsuarioDAO usuarioDAO;
    private VisualizacionDAO visualizacionDAO;
    private ContenidoAudiovisualDAO contenidoDAO;

    public UsuarioService(UsuarioDAO usuarioDAO, VisualizacionDAO visualizacionDAO, ContenidoAudiovisualDAO contenidoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.visualizacionDAO = visualizacionDAO;
        this.contenidoDAO = contenidoDAO;
    }

    public boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }

    public Usuario crearUsuario(String nombre, String email, String nombreUsuario, String clave, LocalDate fechaNacimiento) {
        if (!validarEmail(email)) return null;
        if (usuarioDAO.buscarPorEmail(email) != null) return null;

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setNombreUsuario(nombreUsuario);
        nuevo.setClave(BCrypt.hashpw(clave, BCrypt.gensalt()));
        nuevo.setFechaNacimiento(fechaNacimiento);
        nuevo.setFavoritos(new ArrayList<>());
        nuevo.setHistorialVistos(new ArrayList<>());

        usuarioDAO.guardar(nuevo);
        return nuevo;
    }

    private boolean validarUsuario(Usuario usuario) {
        return usuario != null;
    }

    public Usuario iniciarSesion(String email, String claveIngresada) {
        if (!validarEmail(email)) return null;

        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) return null;

        if (BCrypt.checkpw(claveIngresada, usuario.getClave())) {
            return usuario;
        } else {
            return null;
        }
    }

    public boolean agregarFavorito(Usuario usu, String nombreContenido) {
        if (!validarUsuario(usu)) return false;

        ContenidoAudiovisual contenido = contenidoDAO.buscarPorNombre(nombreContenido);

        if (contenido == null) {
            // Crear un nuevo contenido solo con el nombre (y datos opcionales que no dependan de visualizaciones)
            contenido = new ContenidoAudiovisual();
            contenido.setNombre(nombreContenido);
            // Podés agregar género, fecha, etc. si tenés defaults
            contenidoDAO.guardar(contenido);
        }

        if (usu.getFavoritos() == null) {
            usu.setFavoritos(new ArrayList<>());
        }

        ContenidoAudiovisual finalContenido = contenido;
        boolean existe = usu.getFavoritos().stream()
                .anyMatch(c -> c.getId() == finalContenido.getId());

        if (existe) return false;

        usu.getFavoritos().add(contenido);
        usuarioDAO.guardar(usu);
        return true;
    }



    public List<ContenidoAudiovisual> obtenerFavoritos(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();
        Usuario usuarioDB = usuarioDAO.buscarPorId(usu.getId());
        if (usuarioDB == null || usuarioDB.getFavoritos() == null) return new ArrayList<>();
        return usuarioDB.getFavoritos();
    }


    public boolean agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido) {
        if (!validarUsuario(usu)) return false;

        ContenidoAudiovisual contenidoDB = contenidoDAO.buscarPorNombre(contenido.getNombre());
        if (contenidoDB == null) {
            contenidoDB = contenidoDAO.guardar(contenido);
        }

        if (usu.getHistorialVistos() == null) {
            usu.setHistorialVistos(new ArrayList<>());
        }

        // Validación en la DB para evitar duplicados
        Visualizacion existente = visualizacionDAO.buscarPorUsuarioYContenido(usu.getId(), contenidoDB.getId());
        if (existente != null) return false;

        // Crear visualización
        Visualizacion v = new Visualizacion();
        v.setUsuario(usu);
        v.setContenido(contenidoDB);
        v.setFechaVisto(LocalDate.now());

        visualizacionDAO.guardar(v);
        usu.getHistorialVistos().add(v);
        usuarioDAO.guardar(usu);

        return true;
    }

    public List<Visualizacion> obtenerHistorial(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();
        List<Visualizacion> historial = usuarioDAO.obtenerHistorialCompleto(usu.getId());
        if (historial == null) return new ArrayList<>();
        return historial;
    }

    public boolean puntuarContenido(Usuario usu, ContenidoAudiovisual contenido, int valor) {
        if (!validarUsuario(usu) || valor < 1 || valor > 5) return false;

        Visualizacion v = visualizacionDAO.buscarPorUsuarioYContenido(usu.getId(), contenido.getId());
        if (v == null) return false;

        v.setPuntuacion(valor);
        contenido.setPuntuacionEnEstrellas(valor);

        visualizacionDAO.guardar(v);
        usuarioDAO.guardar(usu);
        return true;
    }
}
/*package com.filmtrack.Services;

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

public class UsuarioService {
    private UsuarioDAO usuarioDAO;
    private VisualizacionDAO visualizacionDAO;
    private ContenidoAudiovisualDAO contenidoDAO;

    public UsuarioService(UsuarioDAO usuarioDAO, VisualizacionDAO visualizacionDAO, ContenidoAudiovisualDAO contenidoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.visualizacionDAO = visualizacionDAO;
        this.contenidoDAO = contenidoDAO;
    }

    //valida el formato de email xx@yyy
    public boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }
    //Metodo crear usuario
    public Usuario crearUsuario(String nombre, String email, String nombreUsuario, String clave, LocalDate fechaNacimiento) {
        //valida formato de email
        if (!validarEmail(email)) {
        return null;
    }
        // valida email registrado
        if (usuarioDAO.buscarPorEmail(email) != null) {
            return null;
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setNombreUsuario(nombreUsuario);
        //encripta la clave
        nuevo.setClave(BCrypt.hashpw(clave, BCrypt.gensalt()));
        nuevo.setFechaNacimiento(fechaNacimiento);
        nuevo.setFavoritos(new ArrayList<>());
        nuevo.setHistorialVistos(new ArrayList<>());

        usuarioDAO.guardar(nuevo);
        return nuevo;
    }

    private boolean validarUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }
        return true;
    }

    public Usuario iniciarSesion(String email, String claveIngresada) {
        if (!validarEmail(email)) {
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) {
            return null;
        }

        if (BCrypt.checkpw(claveIngresada, usuario.getClave())) {
            return usuario;
        } else {
            return null;
        }
    }

    // Metodo agregarFavorito: añade un contenido a los favoritos del usuario
    public boolean agregarFavorito(Usuario usu, ContenidoAudiovisual contenido) {
        // Valida que el usuario exista
        if (!validarUsuario(usu)) return false;

        // Busca contenido por nombre en la base
        ContenidoAudiovisual contenidoDB = contenidoDAO.buscarPorNombre(contenido.getNombre());

        // Si no existe, guardarlo automáticamente
        if (contenidoDB == null) {
            contenidoDB = contenidoDAO.guardar(contenido);
        }

        // Inicializa la lista de favoritos si es null
        if (usu.getFavoritos() == null) {
            usu.setFavoritos(new ArrayList<>());
        }

        // Verifica si el contenido ya está en favoritos
        ContenidoAudiovisual finalContenidoDB = contenidoDB;
        boolean existe = usu.getFavoritos().stream()
                .anyMatch(c -> c.getId() == finalContenidoDB.getId());

        if (existe) {
            return false; // ya estaba agregado
        }

        // Agrega a favoritos si no existe
        usu.getFavoritos().add(contenidoDB);
        usuarioDAO.guardar(usu);
        return true; // agregado exitosamente
    }

    // Metodo obtenerFavoritos: devuelve la lista de favoritos de un usuario
    public List<ContenidoAudiovisual> obtenerFavoritos(Usuario usu) {
        // Validar usuario
        if (!validarUsuario(usu)) return new ArrayList<>();

        // Obtener usuario persistido de la base
        Usuario usuarioDB = usuarioDAO.buscarPorId(usu.getId());
        if (usuarioDB == null || usuarioDB.getFavoritos() == null) {
            return new ArrayList<>();
        }

        // Devolver la lista de favoritos
        return usuarioDB.getFavoritos();
    }

    // Metodo agregarAlHistorial: añade un contenido al historial del usuario
    public boolean agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido) {
        // Validar que el usuario exista
        if (!validarUsuario(usu)) return false;

        // Buscar contenido por nombre en la base
        ContenidoAudiovisual contenidoDB = contenidoDAO.buscarPorNombre(contenido.getNombre());

        // Si no existe, guardarlo automáticamente
        if (contenidoDB == null) {
            contenidoDB = contenidoDAO.guardar(contenido);
        }

        // Inicializar la lista de historial si es null
        if (usu.getHistorialVistos() == null) {
            usu.setHistorialVistos(new ArrayList<>());
        }

        // Verificar si el contenido ya está en el historial (en memoria)
        ContenidoAudiovisual finalContenidoDB = contenidoDB;
        boolean existeEnMemoria = usu.getHistorialVistos().stream()
                .anyMatch(v -> v.getContenido().getId() == finalContenidoDB.getId());

        if (existeEnMemoria) {
            return false; // ya estaba agregado localmente
        }

        // 🔹 Nueva validación contra la base de datos (por si se desincronizó)
        Visualizacion existente = visualizacionDAO.buscarPorUsuarioYContenido(usu.getId(), contenidoDB.getId());
        if (existente != null) {
            return false; // ya estaba en la DB
        }

        // Crear nueva visualización y asociarla al usuario y contenido
        Visualizacion v = new Visualizacion();
        v.setUsuario(usu);
        v.setContenido(contenidoDB);
        v.setFechaVisto(LocalDate.now());

        // Guardar la visualización en la base
        visualizacionDAO.guardar(v);

        // Agregar al historial del usuario y persistir
        usu.getHistorialVistos().add(v);
        usuarioDAO.guardar(usu);

        return true; // agregado exitosamente
    }


    // Metodo obtenerHistorial: devuelve la lista de visualizaciones de un usuario
    public List<Visualizacion> obtenerHistorial(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();

        List<Visualizacion> historial = usuarioDAO.obtenerHistorialCompleto(usu.getId());
        if (historial == null) return new ArrayList<>();

        return historial;
    }

    // Metodo puntuarContenido: Devuelve true si se actualizó correctamente, false si no
    public boolean puntuarContenido(Usuario usu, ContenidoAudiovisual contenido, int valor) {
        if (!validarUsuario(usu) || valor < 1 || valor > 5) return false;

        if (usu.getHistorialVistos() == null) return false;

        for (Visualizacion v : usu.getHistorialVistos()) {
            if (v.getContenido().getId() == contenido.getId()) {
                v.setPuntuacion(valor);
                contenido.setPuntuacionEnEstrellas(valor);

                visualizacionDAO.guardar(v);
                usuarioDAO.guardar(usu);
                return true; // puntuación agregada
            }
        }

        return false; // contenido no estaba en historial
    }

}*/
