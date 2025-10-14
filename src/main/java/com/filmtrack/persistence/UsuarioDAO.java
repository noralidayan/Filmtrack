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
    private EntityManagerFactory emf;
    private EntityManager em;

    public UsuarioDAO() {
        emf = Persistence.createEntityManagerFactory("FilmtrackPU");
        em = emf.createEntityManager();
    }

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

    public void eliminar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, usuario.getId());
            if (u != null) em.remove(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void actualizar(Usuario usuario) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(usuario); // merge actualiza el objeto en la DB
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


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
