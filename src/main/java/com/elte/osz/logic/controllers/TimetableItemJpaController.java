/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.TimetableItem;
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
public class TimetableItemJpaController implements Serializable {

    public TimetableItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TimetableItem timetableItem) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(timetableItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TimetableItem timetableItem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            timetableItem = em.merge(timetableItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = timetableItem.getId();
                if (findTimetableItem(id) == null) {
                    throw new NonexistentEntityException("The timetableItem with id " + id + " no longer exists.");
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
            TimetableItem timetableItem;
            try {
                timetableItem = em.getReference(TimetableItem.class, id);
                timetableItem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The timetableItem with id " + id + " no longer exists.", enfe);
            }
            em.remove(timetableItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TimetableItem> findTimetableItemEntities() {
        return findTimetableItemEntities(true, -1, -1);
    }

    public List<TimetableItem> findTimetableItemEntities(int maxResults, int firstResult) {
        return findTimetableItemEntities(false, maxResults, firstResult);
    }

    private List<TimetableItem> findTimetableItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TimetableItem as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TimetableItem findTimetableItem(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TimetableItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getTimetableItemCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TimetableItem as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
