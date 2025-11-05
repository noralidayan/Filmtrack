package com.filmtrack.service;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import com.filmtrack.repository.VisualizacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 Servicio encargado de manejar las acciones relacionadas con las visualizaciones
 que realiza un usuario dentro del sistema.
 Forma parte de la capa de lógica, donde se implementan las reglas del negocio.
 A través de esta clase se gestiona el registro de visualizaciones, la puntuación
 de contenidos y la obtención del historial.
 Conceptos de POO presentes:
 - Abstracción: representa las operaciones que pertenecen al dominio de visualización.
 - Encapsulamiento: limita el acceso directo al repositorio y protege la lógica de negocio.
 - Responsabilidad única: esta clase se ocupa solo de la gestión de visualizaciones
 y no de la presentación o persistencia directa.
 */
@Service
public class VisualizacionService {

    private final VisualizacionRepository visualizacionRepo;

    public VisualizacionService(VisualizacionRepository visualizacionRepo) {
        this.visualizacionRepo = visualizacionRepo;
    }

    /**
     Registra una nueva visualización asociada a un usuario y a un contenido.
     Si no se indica una fecha, se asigna automáticamente la del día actual.
     Si ya existe una visualización previa del mismo contenido, no se repite.

     @param usuario usuario que realiza la visualización
     @param contenido contenido audiovisual visualizado
     @param fecha fecha de visualización (puede ser null)
     @param puntuacion puntuación en estrellas (1 a 5)
     @return true si se registró correctamente, false si ya existía
     */
    public boolean agregarVisualizacion(Usuario usuario, ContenidoAudiovisual contenido, LocalDate fecha, int puntuacion) {
        boolean yaExiste = visualizacionRepo.existsByUsuarioAndContenido(usuario, contenido);
        if (yaExiste) return false;

        Visualizacion visualizacion = new Visualizacion();
        visualizacion.setUsuario(usuario);
        visualizacion.setContenido(contenido);

        // Si el usuario no ingresó fecha, se usa la del día actual
        visualizacion.setFechaVisto(fecha != null ? fecha : LocalDate.now());

        // Si el usuario no puntuó, se registra 0 = sin puntuar
        visualizacion.setPuntuacion((puntuacion >= 1 && puntuacion <= 5) ? puntuacion : 0);

        visualizacion.setActivo(true);
        visualizacionRepo.save(visualizacion);
        return true;
    }

    public List<Visualizacion> obtenerPorUsuarioId(int idUsuario) {
        return visualizacionRepo.findByUsuarioId(idUsuario);
    }
    /**
     Permite asignar o modificar la puntuación de un contenido previamente visto.
     Si la visualización existe, actualiza su valor y guarda el cambio.
      @param usuario usuario que realiza la puntuación
      @param contenido contenido audiovisual puntuado
      @param valor nuevo valor de puntuación
      @return true si se actualizó correctamente, false si no existía la visualización
     */
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