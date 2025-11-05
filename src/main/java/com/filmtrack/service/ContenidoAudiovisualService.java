package com.filmtrack.service;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
/**
 Servicio encargado de manejar las operaciones relacionadas con los contenidos audiovisuales
 desde el punto de vista del usuario.
 Forma parte de la capa de lógica del sistema, y se encarga de la comunicación
 entre el controlador y la base de datos, manteniendo el código más ordenado y separado por responsabilidades.
 Conceptos de POO presentes:
 - Abstracción: agrupa operaciones que tienen que ver con la lógica del dominio (favoritos).
 - Encapsulamiento: protege el acceso al repositorio de usuarios.
 - Responsabilidad única: esta clase se enfoca únicamente en la gestión de favoritos y no en la persistencia o la vista.
 */

@Service
public class ContenidoAudiovisualService {
    /** Repositorio encargado de la persistencia de datos de usuarios. */
    private final UsuarioRepository usuarioRepository;

    public ContenidoAudiovisualService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     Agrega un contenido audiovisual a la lista de favoritos del usuario.
     Antes de hacerlo, verifica que no se encuentre repetido para evitar duplicados.
     Si el contenido se agrega correctamente, se guarda la actualización en la base de datos.
      @param usuario usuario que marca el contenido como favorito
      @param contenido contenido audiovisual que se desea agregar
      @return true si se agregó correctamente, false si ya estaba en la lista
     */

    public boolean agregarFavorito(Usuario usuario, ContenidoAudiovisual contenido) {
        if (usuario.getFavoritos().contains(contenido)) return false;
        usuario.getFavoritos().add(contenido);
        usuarioRepository.save(usuario);
        return true;
    }
}
