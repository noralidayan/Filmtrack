package com.filmtrack.controller;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contenido")
@CrossOrigin(origins = "http://localhost:5500")
public class ContenidoAudiovisualController {

    private final UsuarioService usuarioService;

    public ContenidoAudiovisualController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<ContenidoAudiovisual> obtenerContenido(@PathVariable String nombre) {
        ContenidoAudiovisual contenido = usuarioService.obtenerContenidoPorNombre(nombre);

        if (contenido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contenido);
    }
}