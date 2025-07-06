package org.example.dao;

import org.example.entities.WorkSpace;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;



@Repository

@Transactional

public class WorkSpaceDAO {
    @PersistenceContext

    private EntityManager em;



    public void create(WorkSpace ws) {
        em.persist(ws);
    }

    public WorkSpace findById(Long id) {
        return em.find(WorkSpace.class, id);
    }

    public List<WorkSpace> findAll() {
        return em.createQuery("SELECT w FROM WorkSpace w", WorkSpace.class).getResultList();
    }

    public List<WorkSpace> findAvailable() {
        return em.createQuery("SELECT w FROM WorkSpace w WHERE w.available = true", WorkSpace.class).getResultList();
    }

    public void delete(WorkSpace ws) {
        em.remove(em.contains(ws) ? ws : em.merge(ws));
    }

    public void deleteById(Long id) {
        em.createQuery("DELETE w from WorkSpace w WHERE w.id = ?", WorkSpace.class).getResultList();
    }


}