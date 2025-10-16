package com.filmtrack.persistence;

import com.filmtrack.models.ContenidoAudiovisual;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class ContenidoAudiovisualDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ContenidoAudiovisualDAO() {
        emf = Persistence.createEntityManagerFactory("FilmtrackPU");
        em = emf.createEntityManager();
    }

    public ContenidoAudiovisual buscarPorNombre(String nombre) {
        List<ContenidoAudiovisual> resultados = em.createQuery(
                        "SELECT c FROM ContenidoAudiovisual c WHERE c.nombre = :nombre",
                        ContenidoAudiovisual.class)
                .setParameter("nombre", nombre)
                .getResultList();

        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public ContenidoAudiovisual guardar(ContenidoAudiovisual contenido) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        if (contenido.getId() == 0) {
            em.persist(contenido);
        } else {
            contenido = em.merge(contenido);
        }

        tx.commit();
        return contenido;
    }



    public ContenidoAudiovisual buscarPorId(int id) {
        return em.find(ContenidoAudiovisual.class, id);
    }
}