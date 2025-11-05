package com.filmtrack.repository;

import com.filmtrack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 Repositorio que administra el acceso a los datos de los usuarios.
 Extiende JpaRepository para heredar las operaciones CRUD básicas.
 Conceptos de POO presentes:
 - Abstracción: define una interfaz que separa la lógica de negocio del acceso a datos.
 - Polimorfismo: permite que Spring Data genere dinámicamente las implementaciones de los métodos declarados.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Busca un usuario por su dirección de email, ignorando mayúsculas o minúsculas.
     * @param email correo electrónico del usuario.
     * @return un Optional con el usuario si se encuentra.
     */
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}