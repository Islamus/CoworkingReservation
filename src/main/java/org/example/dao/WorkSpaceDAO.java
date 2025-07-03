package org.example.dao;

import org.example.entities.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class WorkSpaceDAO {

    private final EntityManager em;

    public WorkSpaceDAO(EntityManager em) {
        this.em = em;
    }

    public void create(WorkSpace workspace) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(workspace);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public WorkSpace findById(Long id) {
        return em.find(WorkSpace.class, id);
    }

    public void update(WorkSpace workspace) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(workspace);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(WorkSpace workspace) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(workspace) ? workspace : em.merge(workspace));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<WorkSpace> findAll() {
        return em.createQuery("SELECT w FROM WorkSpace w", WorkSpace.class).getResultList();
    }

    public List<WorkSpace> findAvailable() {
        return em.createQuery("SELECT w FROM WorkSpace w WHERE w.available = true", WorkSpace.class)
                .getResultList();
    }
}
