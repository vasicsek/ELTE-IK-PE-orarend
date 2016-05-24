/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.Semester;
import com.elte.osz.logic.entities.SemesterItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author RMUGLK
 */
public class SemesterJpaController implements Serializable {

    public SemesterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<SemesterItem> findSemesterItems(Semester semester, String subjectName) {
        List<SemesterItem> result = semester.getItemsAsList();

        String str = subjectName.toLowerCase();
        result.forEach((si) -> {
            if (si.getSubject().getName().toLowerCase().contains(str)) {
                result.add(si);
            }
        });

        return result;
    }

    public List<SemesterItem> findSemesterItems(Semester semester, String subjectName, String teacherName) {
        List<SemesterItem> result = semester.getItemsAsList();

        String strName = subjectName.toLowerCase();
        String strTeacher = teacherName.toLowerCase();
        result.forEach((si) -> {
            if (si.getSubject().getName().toLowerCase().contains(strName)
                    && si.getTeacher().getName().toLowerCase().contains(strTeacher)) {
                result.add(si);
            }
        });

        return result;
    }

    public void create(Semester semester) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(semester);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Semester semester) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            semester = em.merge(semester);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = semester.getId();
                if (findSemester(id) == null) {
                    throw new NonexistentEntityException("The semester with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Semester semester) throws NonexistentEntityException {
        destroy(semester.getId());
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Semester semester;
            try {
                semester = em.getReference(Semester.class, id);
                semester.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The semester with id " + id + " no longer exists.", enfe);
            }
            em.remove(semester);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Semester> findSemesterEntities() {
        return findSemesterEntities(true, -1, -1);
    }

    public List<Semester> findSemesterEntities(int maxResults, int firstResult) {
        return findSemesterEntities(false, maxResults, firstResult);
    }

    private List<Semester> findSemesterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Semester as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Semester findSemester(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Semester.class, id);
        } finally {
            em.close();
        }
    }

    public int getSemesterCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Semester as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
