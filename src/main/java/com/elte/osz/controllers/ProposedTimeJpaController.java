/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.controllers;

import com.elte.osz.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.controllers.exceptions.PreexistingEntityException;
import com.elte.osz.logic.entities.ProposedTime;
import com.elte.osz.logic.entities.Syllabus;
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
public class ProposedTimeJpaController implements Serializable {

    public ProposedTimeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProposedTime proposedTime) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(proposedTime);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProposedTime(proposedTime.getSyllabus()) != null) {
                throw new PreexistingEntityException("ProposedTime " + proposedTime + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProposedTime proposedTime) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            proposedTime = em.merge(proposedTime);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Syllabus id = proposedTime.getSyllabus();
                if (findProposedTime(id) == null) {
                    throw new NonexistentEntityException("The proposedTime with id " + id + " no longer exists.");
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
            ProposedTime proposedTime;
            try {
                proposedTime = em.getReference(ProposedTime.class, id);
                proposedTime.getSyllabus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proposedTime with id " + id + " no longer exists.", enfe);
            }
            em.remove(proposedTime);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Syllabus id) throws NonexistentEntityException {
        destroy(id.getId());
    }

    public List<ProposedTime> findProposedTimeEntities() {
        return findProposedTimeEntities(true, -1, -1);
    }

    public List<ProposedTime> findProposedTimeEntities(int maxResults, int firstResult) {
        return findProposedTimeEntities(false, maxResults, firstResult);
    }

    private List<ProposedTime> findProposedTimeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ProposedTime as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProposedTime findProposedTime(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProposedTime.class, id);
        } finally {
            em.close();
        }
    }

    public ProposedTime findProposedTime(Syllabus id) {
        return findProposedTime(id.getId());
    }

    public int getProposedTimeCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ProposedTime as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
