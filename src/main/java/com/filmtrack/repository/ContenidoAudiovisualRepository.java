package com.filmtrack.repository;

import com.filmtrack.model.ContenidoAudiovisual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 Repositorio que gestiona el acceso a los datos de los contenidos audiovisuales.
 Extiende JpaRepository para aprovechar las operaciones CRUD provistas por  Spring Data JPA.
 Conceptos de POO presentes:
 - Abstracción: define una interfaz que oculta los detalles del acceso a datos.
 - Polimorfismo: permite a Spring generar dinámicamente la implementación concreta en tiempo de ejecución.
 */
public interface ContenidoAudiovisualRepository extends JpaRepository<ContenidoAudiovisual, Integer> {
    /**
     * Busca un contenido audiovisual por su nombre.
     * @param nombre nombre exacto del contenido.
     * @return un Optional que contiene el contenido si existe.
     */
    Optional<ContenidoAudiovisual> findByNombre(String nombre);
}