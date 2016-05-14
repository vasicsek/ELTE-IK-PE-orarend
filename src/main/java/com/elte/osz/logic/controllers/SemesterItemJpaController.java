/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.controllers.exceptions.PreexistingEntityException;
import com.elte.osz.logic.entities.Semester;
import com.elte.osz.logic.entities.SemesterItem;
import com.elte.osz.logic.entities.Teacher;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 
 * @author RMUGLK
 */
public class SemesterItemJpaController implements Serializable {

    public SemesterItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    /**
     * SemesterItem-k(Tantárgy+időpont+terem) keresése az összes lehetséges paraméterrel.      * 
     * @param semesterId semester azonositó egész szám
     * @param subjectName tantárgy neve
     * @param subjectCode tantárgy kódja
     * @param subjectType tantárgy típusa
     * @param semesterNr ajánlott félév
     * @return SemesterItem lista, találatok
     */
    public List<SemesterItem> searchBySubjectFull(
            Long semesterId, 
            String subjectName, 
            String subjectCode, 
            String subjectType,
            int semesterNr
    ){
      return getEntityManager().createNamedQuery("searchBySubjectFull")
                .setParameter(1, semesterId)
                .setParameter(2, subjectName)
                .setParameter(3, subjectCode)
                .setParameter(4, subjectType)
                .setParameter(5, semesterNr)
                .getResultList();        
    }
    /**
     * SemesterItem-k(Tantárgy+időpont+terem) keresése az összes lehetséges paraméterrel.      * 
     * @param semester semester aminek a semesteritem-eit szeretnénk.
     * @param subjectName tantárgy neve
     * @param subjectCode tantárgy kódja
     * @param subjectType tantárgy típusa
     * @param semesterNr ajánlott félév
     * @return SemesterItem lista, találatok
     */
    public List<SemesterItem> searchBySubjectFull(
            Semester semester, 
            String subjectName, 
            String subjectCode, 
            String subjectType,
            int semesterNr
    ){
        return searchBySubjectFull(semester.getId(), subjectName, subjectCode, subjectType, semesterNr);
    }
     /**
     * SemesterItem-k(Tantárgy+időpont+terem) keresése az összes lehetséges paraméterrel.      * 
     * @param semesterId semester azonositó egész szám
     * @param subjectName tantárgy neve
     * @param subjectCode tantárgy kódja
     * @param subjectType tantárgy típusa
     * @param semesterNr ajánlott félév
     * @return SemesterItem lista, találatok
     */
    public List<SemesterItem> searchBySubjectWithSemesterNr(
            Long semesterId, 
            String subjectName, 
            String subjectCode, 
            int semesterNr
    ){           
        return getEntityManager().createNamedQuery("searchBySubjectWithSemester")
                .setParameter(1, semesterId)
                .setParameter(2, subjectName)
                .setParameter(3, subjectCode)
                .setParameter(4, semesterNr)
                .getResultList();        
    }
        
    public List<SemesterItem> searchBySubjectWithSemesterNr(
            Semester semester, 
            String subjectName, 
            String subjectCode, 
            int semesterNr
    ){
        return searchBySubjectWithSemesterNr(semester.getId(), subjectName, subjectCode, semesterNr);
    }
    public List<SemesterItem> searchBySubject(Long semesterId, String subjectName, String subjectCode) {
        EntityManager em = getEntityManager();
                 
        return getEntityManager().createNamedQuery("searchBySubject")
                .setParameter(1, semesterId)
                .setParameter(2, subjectName)
                .setParameter(3, subjectCode)
                .getResultList();
    }
    public List<SemesterItem> searchBySubject(Semester semester, String subjectName, String subjectCode) {
        return searchBySubject(semester.getId(), subjectName, subjectCode);
    }
    public void create(SemesterItem semesterItem) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(semesterItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSemesterItem(semesterItem.getTeacher()) != null) {
                throw new PreexistingEntityException("SemesterItem " + semesterItem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SemesterItem semesterItem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            semesterItem = em.merge(semesterItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Teacher id = semesterItem.getTeacher();
                if (findSemesterItem(id) == null) {
                    throw new NonexistentEntityException("The semesterItem with id " + id + " no longer exists.");
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
            SemesterItem semesterItem;
            try {
                semesterItem = em.getReference(SemesterItem.class, id);
                semesterItem.getTeacher();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The semesterItem with id " + id + " no longer exists.", enfe);
            }
            em.remove(semesterItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Teacher id) throws NonexistentEntityException {
        destroy(id.getId());
    }

    public List<SemesterItem> findSemesterItemEntities() {
        return findSemesterItemEntities(true, -1, -1);
    }

    public List<SemesterItem> findSemesterItemEntities(int maxResults, int firstResult) {
        return findSemesterItemEntities(false, maxResults, firstResult);
    }

    private List<SemesterItem> findSemesterItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SemesterItem as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SemesterItem findSemesterItem(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SemesterItem.class, id);
        } finally {
            em.close();
        }
    }

    public SemesterItem findSemesterItem(Teacher id) {
        return findSemesterItem(id.getId());
    }

    public int getSemesterItemCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from SemesterItem as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
