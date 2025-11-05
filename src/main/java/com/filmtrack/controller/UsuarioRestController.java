package com.filmtrack.controller;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import com.filmtrack.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 Controlador que gestiona las solicitudes relacionadas con los usuarios del sistema.
 Pertenece a la capa de presentación y cumple el rol de intermediario entre el frontend
 y la lógica de negocio implementada en los servicios.
 Conceptos de POO aplicados:
 - Abstracción: define una interfaz clara de comunicación entre el cliente y la aplicación.
 - Encapsulamiento: delega la lógica a la capa de servicio, evitando exponer detalles internos.
 - Responsabilidad única: se encarga únicamente de recibir las peticiones HTTP y devolver las respuestas correspondientes.
 */
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5500") // Permite que el frontend se comunique con el backend (CORS)
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /** Registra un nuevo usuario en el sistema.
     Recibe los datos enviados desde el frontend, los envía al servicio correspondiente
     y devuelve una respuesta indicando el resultado. */
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.crearUsuario(
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                usuario.getClave(),
                usuario.getFechaNacimiento()
        );
        return (nuevo != null)
                ? ResponseEntity.ok(nuevo)
                : ResponseEntity.badRequest().build();
    }

    /** Inicia sesión verificando las credenciales del usuario.
     Si los datos son correctos, devuelve el objeto Usuario; de lo contrario, un error 401. */
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> body) {
        Usuario usu = usuarioService.iniciarSesion(body.get("email"), body.get("clave"));
        return (usu != null)
                ? ResponseEntity.ok(usu)
                : ResponseEntity.status(401).build();
    }

    /** Devuelve la lista de contenidos marcados como favoritos por el usuario.
     Si el usuario no existe, devuelve un código 404. */
    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<ContenidoAudiovisual>> obtenerFavoritos(@PathVariable int id) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(usuarioService.obtenerFavoritos(usu));
    }

    /** Agrega un nuevo contenido a la lista de favoritos del usuario.
     Recibe los datos del contenido (nombre, género y año de lanzamiento) desde el frontend
     y los asocia al usuario correspondiente. */
    @PostMapping("/{id}/favoritos")
    public ResponseEntity<String> agregarFavorito(
            @PathVariable int id,
            @RequestBody Map<String, String> body) {

        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        String nombre = body.get("nombreContenido");
        String genero = body.get("genero");
        String fechaLanzamiento = body.get("fechaLanzamiento"); // ahora se maneja como String

        boolean agregado = usuarioService.agregarFavorito(usu, nombre, genero, fechaLanzamiento);

        return agregado
                ? ResponseEntity.ok("Agregado a favoritos correctamente.")
                : ResponseEntity.badRequest().body("Ya estaba en favoritos o los datos son inválidos.");
    }

    /** Devuelve el historial de visualizaciones de un usuario determinado.
     Si no se encuentra el usuario, responde con un código 404. */
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<Visualizacion>> obtenerHistorial(@PathVariable int id) {
        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(usuarioService.obtenerHistorial(usu));
    }

    /**
     Registra un nuevo contenido en el historial de visualizaciones de un usuario.
     Permite que el usuario ingrese una fecha y una puntuación opcional desde el frontend.
     Si no se indica una fecha, se guarda la del día actual. Si no se indica puntuación, se asigna 0 (sin puntuar).
     */
    @PostMapping("/{id}/historial")
    public ResponseEntity<String> agregarAlHistorial(
            @PathVariable int id,
            @RequestBody Map<String, Object> body) {

        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        String nombre = body.get("nombreContenido").toString();
        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombre);
        if (contenido == null) contenido = new ContenidoAudiovisual(nombre);

        // Procesa la fecha (si el usuario no envía nada, queda null y el service usa la actual)
        LocalDate fecha = null;
        Object fechaObj = body.get("fechaVisto");
        if (fechaObj != null && !fechaObj.toString().isBlank()) {
            fecha = LocalDate.parse(fechaObj.toString());
        }

        // Procesa la puntuación (si el usuario no envía nada, queda en 0)
        int puntuacion = 0;
        Object puntuacionObj = body.get("puntuacion");
        if (puntuacionObj != null && !puntuacionObj.toString().isBlank()) {
            puntuacion = Integer.parseInt(puntuacionObj.toString());
        }

        boolean agregado = usuarioService.agregarAlHistorial(usu, contenido, fecha, puntuacion);

        return agregado
                ? ResponseEntity.ok("Visualización registrada correctamente.")
                : ResponseEntity.badRequest().body("La visualización ya estaba registrada.");
    }

    /** Permite al usuario puntuar un contenido que haya visualizado previamente.
     Verifica la existencia del usuario y del contenido antes de registrar la puntuación. */
    @PostMapping("/{id}/puntuar")
    public ResponseEntity<String> puntuar(
            @PathVariable int id,
            @RequestBody Map<String, Object> body) {

        Usuario usu = usuarioService.obtenerUsuarioPorId(id);
        if (usu == null) return ResponseEntity.notFound().build();

        String nombre = body.get("nombreContenido").toString();
        int valor = Integer.parseInt(body.get("valor").toString());

        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombre);
        if (contenido == null) return ResponseEntity.badRequest().body("Contenido no existe");

        boolean exito = usuarioService.puntuarContenido(usu, contenido, valor);

        return exito
                ? ResponseEntity.ok("Puntuación guardada")
                : ResponseEntity.badRequest().body("No se puede puntuar: revisa si viste ese contenido");
    }
}
