/*package com.filmtrack.controller;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import com.filmtrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestParam String nombre,
                                    @RequestParam String email,
                                    @RequestParam String nombreUsuario,
                                    @RequestParam String clave,
                                    @RequestParam String fechaNacimiento) {
        LocalDate fecha = LocalDate.parse(fechaNacimiento); // formato yyyy-MM-dd
        return usuarioService.crearUsuario(nombre, email, nombreUsuario, clave, fecha);
    }

    // Iniciar sesión
    @PostMapping("/login")
    public Usuario login(@RequestParam String email,
                         @RequestParam String clave) {
        return usuarioService.iniciarSesion(email, clave);
    }

    // Agregar favorito
    @PostMapping("/{usuarioId}/favoritos")
    public boolean agregarFavorito(@PathVariable int usuarioId,
                                   @RequestParam String nombreContenido) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usu == null) return false;
        return usuarioService.agregarFavorito(usu, nombreContenido);
    }

    // Obtener favoritos
    @GetMapping("/{usuarioId}/favoritos")
    public List<ContenidoAudiovisual> obtenerFavoritos(@PathVariable int usuarioId) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usu == null) return List.of();
        return usuarioService.obtenerFavoritos(usu);
    }

    // Agregar al historial
    @PostMapping("/{usuarioId}/historial")
    public boolean agregarAlHistorial(@PathVariable int usuarioId,
                                      @RequestParam String nombreContenido) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usu == null) return false;

        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombreContenido);
        if (contenido == null) return false;

        return usuarioService.agregarAlHistorial(usu, contenido);
    }

    // Obtener historial
    @GetMapping("/{usuarioId}/historial")
    public List<Visualizacion> obtenerHistorial(@PathVariable int usuarioId) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usu == null) return List.of();
        return usuarioService.obtenerHistorial(usu);
    }

    // Puntuar contenido
    @PostMapping("/{usuarioId}/puntuar")
    public boolean puntuarContenido(@PathVariable int usuarioId,
                                    @RequestParam String nombreContenido,
                                    @RequestParam int valor) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usu == null) return false;

        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombreContenido);
        if (contenido == null) return false;

        return usuarioService.puntuarContenido(usu, contenido, valor);
    }
}
*/