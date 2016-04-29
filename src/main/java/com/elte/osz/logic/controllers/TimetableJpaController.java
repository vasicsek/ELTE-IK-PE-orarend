/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.Timetable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Tóth Ákos
 */
public class TimetableJpaController implements Serializable {

    public TimetableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Timetable timetable) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(timetable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Timetable timetable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            timetable = em.merge(timetable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = timetable.getId();
                if (findTimetable(id) == null) {
                    throw new NonexistentEntityException("The timetable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Timetable timetable;
            try {
                timetable = em.getReference(Timetable.class, id);
                timetable.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The timetable with id " + id + " no longer exists.", enfe);
            }
            em.remove(timetable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Timetable> findTimetableEntities() {
        return findTimetableEntities(true, -1, -1);
    }

    public List<Timetable> findTimetableEntities(int maxResults, int firstResult) {
        return findTimetableEntities(false, maxResults, firstResult);
    }

    private List<Timetable> findTimetableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Timetable as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Timetable findTimetable(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Timetable.class, id);
        } finally {
            em.close();
        }
    }

    public int getTimetableCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Timetable as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
