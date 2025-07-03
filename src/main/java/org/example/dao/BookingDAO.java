package org.example.dao;

import org.example.entities.Booking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDAO {

    private final EntityManager em;

    public BookingDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Booking booking) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(booking);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Booking findById(Long id) {
        return em.find(Booking.class, id);
    }

    public void update(Booking booking) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(booking);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Booking booking) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(booking) ? booking : em.merge(booking));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Booking> findBookingsForWorkspace(Long workspaceId, LocalDateTime start, LocalDateTime end) {
        return em.createQuery(
                        "SELECT b FROM Booking b WHERE b.workspace.id = :wsId AND (:start < b.endTime AND :end > b.startTime)", Booking.class)
                .setParameter("wsId", workspaceId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<Booking> findBookingsByUser(Long userId) {
        return em.createQuery("SELECT b FROM Booking b WHERE b.user.id = :userId", Booking.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
