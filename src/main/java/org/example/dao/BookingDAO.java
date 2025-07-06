package org.example.dao;

import org.example.entities.Booking;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional

public class BookingDAO {
    @PersistenceContext

    private EntityManager em;

    public void create(Booking booking) {
        em.persist(booking);
    }

    public Booking findById(Long id) {
        return em.find(Booking.class, id);
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

    public void delete(Booking booking) {
        em.remove(em.contains(booking) ? booking : em.merge(booking));
    }
}
