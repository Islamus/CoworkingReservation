package org.example;

import org.example.dao.BookingDAO;
import org.example.dao.UserDAO;
import org.example.dao.WorkSpaceDAO;
import org.example.entities.Booking;
import org.example.entities.User;
import org.example.entities.WorkSpace;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static EntityManagerFactory emf;
    static EntityManager em;

    static UserDAO userDAO;
    static WorkSpaceDAO workspaceDAO;
    static BookingDAO bookingDAO;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("bookingPU");
        em = emf.createEntityManager();

        userDAO = new UserDAO(em);
        workspaceDAO = new WorkSpaceDAO(em);
        bookingDAO = new BookingDAO(em);

        System.out.println("=== Welcome to Booking System ===");

        mainMenu();

        close();
    }

    static void mainMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    adminMenu();
                    break;
                case "2":
                    customerFlow();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add a new coworking space");
            System.out.println("2. Remove a coworking space");
            System.out.println("3. View all free Spaces");
            System.out.println("4. Back");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addWorkspace();
                    break;
                case "2":
                    removeWorkspace();
                    break;
                case "3":
                    listAvailableWorkspaces();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void customerFlow() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole("customer");


        userDAO.create(user);

        System.out.println("Welcome, " + name + "!");
        customerMenu(user);

    }

    static void customerMenu(User user) {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Back");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listAvailableWorkspaces();
                    break;
                case "2":
                    makeBooking(user);
                    break;
                case "3":
                    viewMyBookings(user);
                    break;
                case "4":
                    cancelBooking(user);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void addWorkspace() {
        try {
            System.out.print("Enter workspace type (e.g. desk, meeting_room): ");
            String type = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());

            WorkSpace ws = new WorkSpace(type, price, true);

            workspaceDAO.create(ws);

            System.out.println("Workspace added with ID: " + ws.getId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("Error adding workspace: " + e.getMessage());
        }
    }

    static void removeWorkspace() {
        try {
            System.out.print("Enter workspace ID to remove: ");
            Long id = Long.parseLong(scanner.nextLine());

            WorkSpace ws = workspaceDAO.findById(id);
            if (ws == null) {
                System.out.println("Workspace not found.");
                return;
            }

            em.getTransaction().begin();
            workspaceDAO.delete(ws);
            em.getTransaction().commit();

            System.out.println("Workspace removed.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("Error removing workspace: " + e.getMessage());
        }
    }

    static void listAllWorkspaces() {
        List<WorkSpace> list = workspaceDAO.findAll();
        if (list.isEmpty()) {
            System.out.println("No workspaces found.");
        } else {
            for (WorkSpace ws : list) {
                System.out.printf("ID: %d, Type: %s, Price: %.2f, Available: %b%n",
                        ws.getId(), ws.getType(), ws.getPrice(), ws.isAvailable());
            }
        }
    }

    static void listAvailableWorkspaces() {
        List<WorkSpace> list = workspaceDAO.findAvailable();
        if (list.isEmpty()) {
            System.out.println("No available workspaces.");
        } else {
            for (WorkSpace ws : list) {
                System.out.printf("ID: %d, Type: %s, Price: %.2f%n", ws.getId(), ws.getType(), ws.getPrice());
            }
        }
    }

    static void makeBooking(User user) {
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

            if (end.isBefore(start) || end.isEqual(start)) {
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
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("Error making booking: " + e.getMessage());
        }
    }

    static void viewMyBookings(User user) {
        List<Booking> bookings = bookingDAO.findBookingsByUser(user.getId());
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("Your bookings:");
            for (Booking b : bookings) {
                System.out.printf("Booking ID: %d, Workspace ID: %d, Start: %s, End: %s%n",
                        b.getId(),
                        b.getWorkspace().getId(),
                        b.getStartTime(),
                        b.getEndTime());
            }
        }
    }

    static void cancelBooking(User user) {
        try {
            System.out.print("Enter booking ID to cancel: ");
            Long bookingId = Long.parseLong(scanner.nextLine());

            Booking booking = em.find(Booking.class, bookingId);
            if (booking == null || !booking.getUser().getId().equals(user.getId())) {
                System.out.println("Booking not found or you don't have permission to cancel it.");
                return;
            }

            bookingDAO.delete(booking);

            System.out.println("Booking canceled.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("Error canceling booking: " + e.getMessage());
        }
    }

    static void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
        scanner.close();
    }
}
