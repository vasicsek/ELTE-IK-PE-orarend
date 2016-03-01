/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
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
public class SyllabusJpaController implements Serializable {

    public SyllabusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Syllabus syllabus) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(syllabus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Syllabus syllabus) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            syllabus = em.merge(syllabus);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = syllabus.getId();
                if (findSyllabus(id) == null) {
                    throw new NonexistentEntityException("The syllabus with id " + id + " no longer exists.");
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
            Syllabus syllabus;
            try {
                syllabus = em.getReference(Syllabus.class, id);
                syllabus.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The syllabus with id " + id + " no longer exists.", enfe);
            }
            em.remove(syllabus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Syllabus> findSyllabusEntities() {
        return findSyllabusEntities(true, -1, -1);
    }

    public List<Syllabus> findSyllabusEntities(int maxResults, int firstResult) {
        return findSyllabusEntities(false, maxResults, firstResult);
    }

    private List<Syllabus> findSyllabusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Syllabus as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Syllabus findSyllabus(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Syllabus.class, id);
        } finally {
            em.close();
        }
    }

    public int getSyllabusCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Syllabus as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
