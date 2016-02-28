/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.tosz.controllers;

import com.elte.tosz.controllers.exceptions.NonexistentEntityException;
import com.elte.tosz.logic.entities.ProposedTimeItem;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author cauchy
 */
public class ProposedTimeItemJpaController implements Serializable {

    public ProposedTimeItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProposedTimeItem proposedTimeItem) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(proposedTimeItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProposedTimeItem proposedTimeItem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            proposedTimeItem = em.merge(proposedTimeItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = proposedTimeItem.getId();
                if (findProposedTimeItem(id) == null) {
                    throw new NonexistentEntityException("The proposedTimeItem with id " + id + " no longer exists.");
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
            ProposedTimeItem proposedTimeItem;
            try {
                proposedTimeItem = em.getReference(ProposedTimeItem.class, id);
                proposedTimeItem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proposedTimeItem with id " + id + " no longer exists.", enfe);
            }
            em.remove(proposedTimeItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProposedTimeItem> findProposedTimeItemEntities() {
        return findProposedTimeItemEntities(true, -1, -1);
    }

    public List<ProposedTimeItem> findProposedTimeItemEntities(int maxResults, int firstResult) {
        return findProposedTimeItemEntities(false, maxResults, firstResult);
    }

    private List<ProposedTimeItem> findProposedTimeItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ProposedTimeItem as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProposedTimeItem findProposedTimeItem(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProposedTimeItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getProposedTimeItemCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ProposedTimeItem as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
