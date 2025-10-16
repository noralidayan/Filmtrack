package com.filmtrack.persistence;

import com.filmtrack.models.ContenidoAudiovisual;
import com.filmtrack.models.Usuario;
import com.filmtrack.models.Visualizacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    //Atributos para interactuar con la base de datos
    private EntityManagerFactory emf;
    private EntityManager em;
    //Constructor: inicializo conexion con DB y EntityManager
    public UsuarioDAO() {
        emf = Persistence.createEntityManagerFactory("FilmtrackPU");
        em = emf.createEntityManager();
    }
//Aplicando CRUD
    // Metodo guardar: alta o modificación de un usuario
    public void guardar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    // Metodo Eliminar: "borrado" lógico, activo = false
    public void eliminar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, usuario.getId());
            if (u != null) u.setActivo(false);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    //Metodo Buscar un usuario por email. Devuelve null si no existe.
    public Usuario buscarPorEmail(String email) {
        try {
            return em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario buscarPorId(int id) {
        return em.find(Usuario.class, id);
    }
    // Historial de visualizaciones de un usuario
    public List<Visualizacion> obtenerHistorialCompleto(int usuarioId) {
        try {
            return em.createQuery(
                            "SELECT v FROM Visualizacion v " +
                                    "JOIN FETCH v.contenido " +
                                    "WHERE v.usuario.id = :usuarioId", Visualizacion.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void cerrar() {
        em.close();
        emf.close();
    }
}
