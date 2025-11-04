package com.filmtrack.repository;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VisualizacionRepository extends JpaRepository<Visualizacion, Integer> {

    Optional<Visualizacion> findByUsuarioIdAndContenidoId(int usuarioId, int contenidoId);

    List<Visualizacion> findByUsuarioId(int usuarioId);

    boolean existsByUsuarioAndContenido(Usuario usuario, ContenidoAudiovisual contenido);
}