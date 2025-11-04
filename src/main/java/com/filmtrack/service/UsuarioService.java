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

    public boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }

    public Usuario crearUsuario(String nombre, String email, String nombreUsuario, String clave, LocalDate fechaNacimiento) {
        if (!validarEmail(email)) return null;

        Optional<Usuario> existente = usuarioRepository.findByEmail(email);
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

    public Usuario iniciarSesion(String email, String claveIngresada) {
        if (!validarEmail(email)) return null;

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isEmpty()) return null;

        return BCrypt.checkpw(claveIngresada, usuario.get().getClave()) ? usuario.get() : null;
    }

    public boolean agregarFavorito(Usuario usu, String nombreContenido) {
        if (!validarUsuario(usu)) return false;

        ContenidoAudiovisual contenido = contenidoRepository.findByNombre(nombreContenido)
                .orElseGet(() -> {
                    ContenidoAudiovisual nuevo = new ContenidoAudiovisual();
                    nuevo.setNombre(nombreContenido);
                    return contenidoRepository.save(nuevo);
                });

        if (usu.getFavoritos() == null) usu.setFavoritos(new ArrayList<>());

        boolean existe = usu.getFavoritos().stream()
                .anyMatch(c -> c.getId() == contenido.getId());
        if (existe) return false;

        usu.getFavoritos().add(contenido);
        usuarioRepository.save(usu);
        return true;
    }

    public List<ContenidoAudiovisual> obtenerFavoritos(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();
        return usuarioRepository.findById(usu.getId())
                .map(Usuario::getFavoritos)
                .orElse(new ArrayList<>());
    }

    public boolean agregarAlHistorial(Usuario usu, ContenidoAudiovisual contenido) {
        if (!validarUsuario(usu)) return false;

        ContenidoAudiovisual contenidoDB = contenidoRepository.findByNombre(contenido.getNombre())
                .orElseGet(() -> contenidoRepository.save(contenido));

        if (usu.getHistorialVistos() == null) usu.setHistorialVistos(new ArrayList<>());

        Optional<Visualizacion> existente = visualizacionRepository
                .findByUsuarioIdAndContenidoId(usu.getId(), contenidoDB.getId());
        if (existente.isPresent()) return false;

        Visualizacion v = new Visualizacion();
        v.setUsuario(usu);
        v.setContenido(contenidoDB);
        v.setFechaVisto(LocalDate.now());

        visualizacionRepository.save(v);
        usu.getHistorialVistos().add(v);
        usuarioRepository.save(usu);

        return true;
    }

    // 👇 cambio importante: usar findByUsuarioId
    public List<Visualizacion> obtenerHistorial(Usuario usu) {
        if (!validarUsuario(usu)) return new ArrayList<>();
        return visualizacionRepository.findByUsuarioId(usu.getId());
    }

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
