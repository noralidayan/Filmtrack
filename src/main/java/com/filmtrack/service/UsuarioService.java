package com.filmtrack.service;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import com.filmtrack.repository.ContenidoAudiovisualRepository;
import com.filmtrack.repository.UsuarioRepository;
import com.filmtrack.repository.VisualizacionRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 Servicio que maneja las operaciones principales relacionadas con los usuarios.
 Incluye la creación de cuentas, el inicio de sesión y la gestión de favoritos e historial.
 Forma parte de la capa de lógica del sistema, donde se definen las reglas que vinculan
 la interacción del usuario con la base de datos.

 Conceptos de POO presentes:
 - Abstracción: agrupa las acciones del dominio “usuario”.
 - Encapsulamiento: restringe el acceso directo a los repositorios, centralizando la lógica.
 - Responsabilidad única: esta clase se encarga solo de la lógica asociada al usuario,
 separándola del controlador y de la capa de persistencia.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final VisualizacionRepository visualizacionRepository;
    private final ContenidoAudiovisualRepository contenidoRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            VisualizacionRepository visualizacionRepository,
            ContenidoAudiovisualRepository contenidoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.visualizacionRepository = visualizacionRepository;
        this.contenidoRepository = contenidoRepository;
    }

    /** Verifica si un email tiene formato válido mediante expresión regular. */
    public boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }

    /** Crea un nuevo usuario en el sistema con validaciones y encriptación de contraseña. */
    public Usuario crearUsuario(String nombre, String email, String nombreUsuario, String clave, LocalDate fechaNacimiento) {
        if (!validarEmail(email)) return null;

        Optional<Usuario> existente = usuarioRepository.findByEmailIgnoreCase(email);
        if (existente.isPresent()) return null;

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setNombreUsuario(nombreUsuario);
        nuevo.setClave(BCrypt.hashpw(clave, BCrypt.gensalt()));
        nuevo.setFechaNacimiento(fechaNacimiento);
        nuevo.setFavoritos(new ArrayList<>());
        nuevo.setHistorialVistos(new ArrayList<>());

        return usuarioRepository.save(nuevo);
    }

    private boolean validarUsuario(Usuario usuario) {
        return usuario != null;
    }

    /** Inicia sesión comprobando correo y contraseña encriptada. */
    public Usuario iniciarSesion(String email, String claveIngresada) {
        if (!validarEmail(email)) return null;

        Optional<Usuario> usuario = usuarioRepository.findByEmailIgnoreCase(email);
        if (usuario.isEmpty()) return null;

        return BCrypt.checkpw(claveIngresada, usuario.get().getClave()) ? usuario.get() : null;
    }

    /**
     * Método sobrecargado que permite registrar desde el frontend un contenido
     * audiovisual con su nombre, género y año de lanzamiento.
     * Ahora la fecha se maneja como texto (String) para facilitar la carga de datos.
     */
    public boolean agregarFavorito(Usuario usu, String nombreContenido, String genero, String fechaLanzamiento) {
        if (!validarUsuario(usu) || nombreContenido == null || nombreContenido.isBlank()) return false;

        ContenidoAudiovisual contenido = contenidoRepository.findByNombre(nombreContenido)
                .orElseGet(() -> {
                    ContenidoAudiovisual nuevo = new ContenidoAudiovisual();
                    nuevo.setNombre(nombreContenido);
                    nuevo.setGenero(
                            (genero != null && !genero.isBlank())
                                    ? genero
                                    : "Desconocido"
                    );
                    nuevo.setFechaLanzamiento(
                            (fechaLanzamiento != null && !fechaLanzamiento.isBlank())
                                    ? fechaLanzamiento
                                    : "1900-01-01"
                    );
                    return contenidoRepository.save(nuevo);
                });

        if (usu.getFavoritos() == null) usu.setFavoritos(new ArrayList<>());

        boolean yaExiste = usu.getFavoritos().stream()
                .anyMatch(c -> c.getId() == contenido.getId());
        if (yaExiste) return false;

        usu.getFavoritos().add(contenido);
        usuarioRepository.save(usu);
        return true;
    }

    /** Devuelve los contenidos marcados como favoritos por el usuario. */
    public List<ContenidoAudiovisual> obtenerFavoritos(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();
        return usuarioRepository.findById(usu.getId())
                .map(Usuario::getFavoritos)
                .orElse(new ArrayList<>());
    }

    /**
     Agrega un contenido al historial de visualizaciones del usuario.
     Permite registrar fecha y puntuación si el frontend las proporciona.
     Si no se envían, se guarda la fecha actual y la puntuación por defecto (0 = sin puntuar).
     */
    public boolean agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido, LocalDate fecha, int puntuacion) {
        if (!validarUsuario(usu)) return false;

        ContenidoAudiovisual contenidoDB = contenidoRepository.findByNombre(contenido.getNombre())
                .orElseGet(() -> contenidoRepository.save(contenido));

        Optional<Visualizacion> existente = visualizacionRepository
                .findByUsuarioIdAndContenidoId(usu.getId(), contenidoDB.getId());
        if (existente.isPresent()) return false;

        Visualizacion v = new Visualizacion();
        v.setUsuario(usu);
        v.setContenido(contenidoDB);
        v.setFechaVisto(fecha != null ? fecha : LocalDate.now());
        v.setPuntuacion((puntuacion >= 1 && puntuacion <= 5) ? puntuacion : 0);
        v.setActivo(true);

        visualizacionRepository.save(v);

        if (usu.getHistorialVistos() == null) usu.setHistorialVistos(new ArrayList<>());
        usu.getHistorialVistos().add(v);
        usuarioRepository.save(usu);

        return true;
    }

    /** Obtiene todas las visualizaciones registradas de un usuario. */
    public List<Visualizacion> obtenerHistorial(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();
        return visualizacionRepository.findByUsuarioId(usu.getId());
    }

    /** Permite modificar la puntuación de un contenido ya visto. */
    public boolean puntuarContenido(Usuario usu, ContenidoAudiovisual contenido, int valor) {
        if (!validarUsuario(usu) || valor < 1 || valor > 5) return false;

        Optional<Visualizacion> vOpt = visualizacionRepository.findByUsuarioIdAndContenidoId(usu.getId(), contenido.getId());
        if (vOpt.isEmpty()) return false;

        Visualizacion v = vOpt.get();
        v.setPuntuacion(valor);
        contenido.setPuntuacionEnEstrellas(valor);

        visualizacionRepository.save(v);
        usuarioRepository.save(usu);
        return true;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public ContenidoAudiovisual obtenerContenidoPorNombre(String nombre) {
        return contenidoRepository.findByNombre(nombre).orElse(null);
    }
}
