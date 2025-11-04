package com.filmtrack.service;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import com.filmtrack.repository.VisualizacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VisualizacionService {

    private final VisualizacionRepository visualizacionRepo;

    public VisualizacionService(VisualizacionRepository visualizacionRepo) {
        this.visualizacionRepo = visualizacionRepo;
    }

    public boolean agregarVisualizacion(Usuario usuario, ContenidoAudiovisual contenido, LocalDate fecha, int puntuacion) {
        boolean yaExiste = visualizacionRepo.existsByUsuarioAndContenido(usuario, contenido);
        if (yaExiste) return false;

        Visualizacion visualizacion = new Visualizacion();
        visualizacion.setUsuario(usuario);
        visualizacion.setContenido(contenido);
        visualizacion.setFechaVisto(fecha != null ? fecha : LocalDate.now());
        visualizacion.setPuntuacion(puntuacion);
        visualizacion.setActivo(true);

        visualizacionRepo.save(visualizacion);
        return true;
    }

    public List<Visualizacion> obtenerPorUsuarioId(int idUsuario) {
        return visualizacionRepo.findByUsuarioId(idUsuario);
    }

    public boolean puntuarContenido(Usuario usuario, ContenidoAudiovisual contenido, int valor) {
        Optional<Visualizacion> existente =
                visualizacionRepo.findByUsuarioIdAndContenidoId(usuario.getId(), contenido.getId());

        if (existente.isPresent()) {
            Visualizacion visualizacion = existente.get();
            visualizacion.setPuntuacion(valor);
            visualizacionRepo.save(visualizacion);
            return true;
        } else {
            return false;
        }
    }

}