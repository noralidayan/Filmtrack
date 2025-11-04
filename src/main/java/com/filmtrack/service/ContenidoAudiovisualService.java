package com.filmtrack.service;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ContenidoAudiovisualService {

    private final UsuarioRepository usuarioRepository;

    public ContenidoAudiovisualService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // meodo para agregar un favorito
    public boolean agregarFavorito(Usuario usuario, ContenidoAudiovisual contenido) {
        if (usuario.getFavoritos().contains(contenido)) return false;
        usuario.getFavoritos().add(contenido);
        usuarioRepository.save(usuario);
        return true;
    }
}
