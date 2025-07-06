package org.example.services;

import org.example.dao.BookingDAO;
import org.example.dao.WorkSpaceDAO;
import org.example.entities.Booking;
import org.example.entities.User;
import org.example.entities.WorkSpace;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class BookingService {
    private final BookingDAO bookingDAO;
    private final WorkSpaceDAO workspaceDAO;

    public BookingService(BookingDAO bookingDAO, WorkSpaceDAO workspaceDAO) {
        this.bookingDAO = bookingDAO;
        this.workspaceDAO = workspaceDAO;
    }

    public void makeBooking(Scanner scanner, User user) {
        try {
            System.out.print("Enter workspace ID to book: ");
            Long wsId = Long.parseLong(scanner.nextLine());
            WorkSpace ws = workspaceDAO.findById(wsId);
            if (ws == null || !ws.isAvailable()) {
                System.out.println("Workspace not available.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            System.out.print("Enter booking start (yyyy-MM-dd HH:mm): ");
            LocalDateTime start = LocalDateTime.parse(scanner.nextLine(), formatter);

            System.out.print("Enter booking end (yyyy-MM-dd HH:mm): ");
            LocalDateTime end = LocalDateTime.parse(scanner.nextLine(), formatter);

            if (!end.isAfter(start)) {
                System.out.println("End time must be after start time.");
                return;
            }

            List<Booking> conflicts = bookingDAO.findBookingsForWorkspace(wsId, start, end);
            if (!conflicts.isEmpty()) {
                System.out.println("This workspace is already booked for the selected time.");
                return;
            }

            Booking booking = new Booking(user, ws, start, end);
            bookingDAO.create(booking);
            System.out.println("Booking successful! ID: " + booking.getId());
        } catch (Exception e) {
            System.out.println("Error making booking: " + e.getMessage());
        }
    }

    public void viewMyBookings(User user) {
        List<Booking> bookings = bookingDAO.findBookingsByUser(user.getId());
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            for (Booking b : bookings) {
                System.out.printf("Booking ID: %d, Workspace ID: %d, Start: %s, End: %s%n",
                        b.getId(), b.getWorkspace().getId(), b.getStartTime(), b.getEndTime());
            }
        }
    }

    public void cancelBooking(Scanner scanner, User user) {
        try {
            System.out.print("Enter booking ID to cancel: ");
            Long bookingId = Long.parseLong(scanner.nextLine());
            Booking booking = bookingDAO.findById(bookingId);
            if (booking == null || !booking.getUser().getId().equals(user.getId())) {
                System.out.println("Booking not found or not yours.");
                return;
            }
            bookingDAO.delete(booking);
            System.out.println("Booking canceled.");
        } catch (Exception e) {
            System.out.println("Error canceling booking: " + e.getMessage());
        }
    }
}
