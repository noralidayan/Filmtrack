package com.filmtrack.repository;

import com.filmtrack.model.ContenidoAudiovisual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContenidoAudiovisualRepository extends JpaRepository<ContenidoAudiovisual, Integer> {
    Optional<ContenidoAudiovisual> findByNombre(String nombre);
}