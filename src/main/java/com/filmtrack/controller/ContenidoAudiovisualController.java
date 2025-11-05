package com.filmtrack.controller;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
  Controlador que gestiona las solicitudes vinculadas a los contenidos audiovisuales.
  Pertenece a la capa de presentación y actúa como enlace entre el frontend y la lógica de negocio.
  Conceptos de POO aplicados:
  - Abstracción: define una interfaz clara para la comunicación con el sistema.
  - Encapsulamiento: delega las operaciones al servicio correspondiente.
  - Responsabilidad única: se encarga solo de recibir peticiones y enviar respuestas.
 */

@RestController //esta clase va a manejar solicitudes HTTP (como GET, POST, PUT, DELETE) y devolver resultados en formato JSON.
@RequestMapping("/contenido") //Todas las URLs que empiecen con /contenido van a ser manejadas por esta clase.
@CrossOrigin(origins = "http://localhost:5500") // Habilita la conexión entre el frontend y el backend aunque estén en diferentes direcciones (CORS).

public class ContenidoAudiovisualController {

    /** Servicio que gestiona las operaciones de usuario relacionadas con los contenidos. */
    private final UsuarioService usuarioService;

    public ContenidoAudiovisualController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
      Obtiene un contenido audiovisual por su nombre.
      Si el contenido no existe, devuelve un código 404 (Not Found).
      @param nombre nombre del contenido audiovisual a buscar
      @return ResponseEntity con el contenido encontrado o un error si no existe
     */
    @GetMapping("/{nombre}")
    public ResponseEntity<ContenidoAudiovisual> obtenerContenido(@PathVariable String nombre) {
        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombre);

        if (contenido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contenido);
    }
}