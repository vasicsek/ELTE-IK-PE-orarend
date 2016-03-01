/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.controllers;

import com.elte.osz.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.controllers.exceptions.PreexistingEntityException;
import com.elte.osz.logic.entities.Subject;
import com.elte.osz.logic.entities.SyllabusItem;
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
public class SyllabusItemJpaController implements Serializable {

    public SyllabusItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SyllabusItem syllabusItem) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(syllabusItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSyllabusItem(syllabusItem.getSubject()) != null) {
                throw new PreexistingEntityException("SyllabusItem " + syllabusItem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SyllabusItem syllabusItem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            syllabusItem = em.merge(syllabusItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Subject id = syllabusItem.getSubject();
                if (findSyllabusItem(id) == null) {
                    throw new NonexistentEntityException("The syllabusItem with id " + id + " no longer exists.");
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
            SyllabusItem syllabusItem;
            try {
                syllabusItem = em.getReference(SyllabusItem.class, id);
                syllabusItem.getSubject();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The syllabusItem with id " + id + " no longer exists.", enfe);
            }
            em.remove(syllabusItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Subject id) throws NonexistentEntityException {
        destroy(id.getId());
    }

    public List<SyllabusItem> findSyllabusItemEntities() {
        return findSyllabusItemEntities(true, -1, -1);
    }

    public List<SyllabusItem> findSyllabusItemEntities(int maxResults, int firstResult) {
        return findSyllabusItemEntities(false, maxResults, firstResult);
    }

    private List<SyllabusItem> findSyllabusItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SyllabusItem as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SyllabusItem findSyllabusItem(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SyllabusItem.class, id);
        } finally {
            em.close();
        }
    }

    public SyllabusItem findSyllabusItem(Subject id) {
        return findSyllabusItem(id.getId());
    }

    public int getSyllabusItemCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from SyllabusItem as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
