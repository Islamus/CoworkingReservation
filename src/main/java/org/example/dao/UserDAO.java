package org.example.dao;

import org.example.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

@Transactional

public class UserDAO {
    @PersistenceContext

    private EntityManager em;


    public void create(User user) {
        em.persist(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}