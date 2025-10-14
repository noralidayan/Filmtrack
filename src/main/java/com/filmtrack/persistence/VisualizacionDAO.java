package com.filmtrack.persistence;

import com.filmtrack.models.Visualizacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class VisualizacionDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public VisualizacionDAO() {
        emf = Persistence.createEntityManagerFactory("FilmtrackPU");
        em = emf.createEntityManager();
    }

    public void guardar(Visualizacion visualizacion) {
        try {
            em.getTransaction().begin();

            // asegurarse que usuario y contenido ya existen en la DB
            if (!em.contains(visualizacion.getUsuario())) {
                visualizacion.setUsuario(em.merge(visualizacion.getUsuario()));
            }
            if (!em.contains(visualizacion.getContenido())) {
                visualizacion.setContenido(em.merge(visualizacion.getContenido()));
            }

            em.persist(visualizacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void cerrar() {
        em.close();
        emf.close();
    }
}
