/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.TimetableHead;
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
public class TimetableHeadJpaController implements Serializable {

    public TimetableHeadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TimetableHead timetableHead) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(timetableHead);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TimetableHead timetableHead) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            timetableHead = em.merge(timetableHead);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = timetableHead.getId();
                if (findTimetableHead(id) == null) {
                    throw new NonexistentEntityException("The timetableHead with id " + id + " no longer exists.");
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
            TimetableHead timetableHead;
            try {
                timetableHead = em.getReference(TimetableHead.class, id);
                timetableHead.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The timetableHead with id " + id + " no longer exists.", enfe);
            }
            em.remove(timetableHead);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TimetableHead> findTimetableHeadEntities() {
        return findTimetableHeadEntities(true, -1, -1);
    }

    public List<TimetableHead> findTimetableHeadEntities(int maxResults, int firstResult) {
        return findTimetableHeadEntities(false, maxResults, firstResult);
    }

    private List<TimetableHead> findTimetableHeadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TimetableHead as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TimetableHead findTimetableHead(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TimetableHead.class, id);
        } finally {
            em.close();
        }
    }

    public int getTimetableHeadCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TimetableHead as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
