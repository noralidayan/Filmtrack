package com.filmtrack.repository;

import com.filmtrack.model.ContenidoAudiovisual;
import com.filmtrack.model.Usuario;
import com.filmtrack.model.Visualizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
/**
 Repositorio que gestiona el acceso a los datos de las visualizaciones.
 Extiende JpaRepository para disponer de todas las operaciones CRUD (crear, leer, actualizar, eliminar).
 Conceptos de POO presentes:
 - Abstracción: define un contrato para acceder a los datos sin exponer la implementación.
 - Polimorfismo: permite que Spring genere dinámicamente la clase que implementa esta interfaz.
 - Encapsulamiento: el acceso a los datos se maneja de forma controlada y centralizada.
 */
public interface VisualizacionRepository extends JpaRepository<Visualizacion, Integer> {
    /**
      Busca una visualización específica asociada a un usuario y a un contenido.
      Útil para verificar si un usuario ya visualizó un contenido determinado.
      @param usuarioId identificador del usuario
      @param contenidoId identificador del contenido audiovisual
      @return un Optional con la visualización si existe
     */
    Optional<Visualizacion> findByUsuarioIdAndContenidoId(int usuarioId, int contenidoId);

    /**
      Devuelve todas las visualizaciones registradas por un usuario.
      Se utiliza para obtener su historial completo.
      @param usuarioId identificador del usuario
      @return lista de visualizaciones asociadas al usuario
     */
    List<Visualizacion> findByUsuarioId(int usuarioId);
    /**
      Verifica si ya existe una visualización registrada entre un usuario y un contenido.
      Evita duplicaciones en la base de datos.
      @param usuario usuario al que pertenece la visualización
      @param contenido contenido audiovisual visto
      @return true si ya existe, false si no
     */
    boolean existsByUsuarioAndContenido(Usuario usuario, ContenidoAudiovisual contenido);
}