/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.controllers;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.RoomGroup;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author toarabi
 */
public class RoomGroupJpaController implements Serializable {

    public RoomGroupJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RoomGroup roomGroup) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(roomGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RoomGroup roomGroup) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            roomGroup = em.merge(roomGroup);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = roomGroup.getId();
                if (findRoomGroup(id) == null) {
                    throw new NonexistentEntityException("The roomGroup with id " + id + " no longer exists.");
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
            RoomGroup roomGroup;
            try {
                roomGroup = em.getReference(RoomGroup.class, id);
                roomGroup.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roomGroup with id " + id + " no longer exists.", enfe);
            }
            em.remove(roomGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RoomGroup> findRoomGroupEntities() {
        return findRoomGroupEntities(true, -1, -1);
    }

    public List<RoomGroup> findRoomGroupEntities(int maxResults, int firstResult) {
        return findRoomGroupEntities(false, maxResults, firstResult);
    }

    private List<RoomGroup> findRoomGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from RoomGroup as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RoomGroup findRoomGroup(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RoomGroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoomGroupCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from RoomGroup as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
