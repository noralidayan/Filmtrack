package com.filmtrack.controller;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import com.filmtrack.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.crearUsuario(
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                usuario.getClave(),
                usuario.getFechaNacimiento()
        );
        if (nuevo != null) {
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<ContenidoAudiovisual>> obtenerFavoritos(@PathVariable int id) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(usuarioService.obtenerFavoritos(usu));
    }

    @GetMapping("/{id}/historial")
    public ResponseEntity<List<Visualizacion>> obtenerHistorial(@PathVariable int id) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(usuarioService.obtenerHistorial(usu));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> body) {
        Usuario usu = usuarioService.iniciarSesion(body.get("email"), body.get("clave"));
        return usu != null ? ResponseEntity.ok(usu) : ResponseEntity.status(401).build();
    }

    @PostMapping("/{id}/favoritos")
    public ResponseEntity<String> agregarFavorito(
            @PathVariable int id,
            @RequestBody Map<String, String> body) {

        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        boolean agregado = usuarioService.agregarFavorito(usu, body.get("nombreContenido"));
        return agregado ? ResponseEntity.ok("Agregado a favoritos")
                : ResponseEntity.badRequest().body("Ya estaba en favoritos");
    }


    @PostMapping("/{id}/historial")
    public ResponseEntity<String> agregarAlHistorial(
            @PathVariable int id,
            @RequestBody Map<String, String> body) {

        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        String nombre = body.get("nombreContenido");
        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombre);

        if (contenido == null) contenido = new ContenidoAudiovisual(nombre);

        boolean agregado = usuarioService.agregarAlHistorial(usu, contenido);
        return agregado ? ResponseEntity.ok("Agregado al historial")
                : ResponseEntity.badRequest().body("Ya estaba en el historial");
    }

    @PostMapping("/{id}/puntuar")
    public ResponseEntity<String> puntuar(
            @PathVariable int id,
            @RequestBody Map<String, Object> body) {

        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        String nombre = body.get("nombreContenido").toString();
        int valor = (int) body.get("valor");

        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombre);
        if (contenido == null) return ResponseEntity.badRequest().body("Contenido no existe");

        boolean exito = usuarioService.puntuarContenido(usu, contenido, valor);

        return exito ? ResponseEntity.ok("Puntuación guardada")
                : ResponseEntity.badRequest().body("No se puede puntuar: revisa si viste ese contenido");
    }
}