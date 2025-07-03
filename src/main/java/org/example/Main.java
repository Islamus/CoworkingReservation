package org.example;

import org.example.coworking.DBConnection;
import org.example.dao.ReservationDAO;
import org.example.dao.WorkspaceDAO;
import org.example.expections.MyCustomExpe;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final String WORKSPACES_FILE = "C:/Users/Islam/Desktop/workspaces.txt";
    static final String RESERVATIONS_FILE = "reservations.txt";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws MyCustomExpe, SQLException {
        DBConnection.getConnection();
        while (true) {
            System.out.println("\n=== Reservation System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminMenu();
                    break;
                case "2":
                    customerMenu();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void adminMenu() throws MyCustomExpe {
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
                    removeWorkspaceReservation();
                    break;
                case "3":
                    viewAllWorkSpaces();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void customerMenu() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

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
                    viewAllWorkSpaces();
                    break;
                case "2":
                    makeReservation(name);
                    break;
                case "3":
                    viewUserReservations(name);
                    break;
                case "4":
                    cancelReservation(name);
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
            System.out.print("Enter workspace ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter type (Private, Open): ");
            String type = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());

            WorkSpace ws = new WorkSpace(id, type, price, true);
            WorkspaceDAO dao = new WorkspaceDAO();
            dao.insertWorkspace(ws);
            System.out.println("Workspace added to database.");
        } catch (Exception e) {
            System.out.println("Error adding workspace: " + e.getMessage());
        }
    }


    static void removeWorkspaceReservation() throws MyCustomExpe {
        System.out.print("Enter workspace ID to remove: ");
            String id = scanner.nextLine().trim();
            List<String> lines = FileReaderHelper.readLines(WORKSPACES_FILE);


        List<String> updated = lines.stream()
                .filter(line -> {
                    WorkSpace ws = WorkSpace.fromFileString(line);
                    return !ws.getId().equals(id);
                })
                .collect(Collectors.toList());


        for (String line : lines) {
            if (!line.startsWith(id + ",")) {
                updated.add(line);
            }

            if(!id.equals(line)){
                throw new MyCustomExpe("Such ID does not exist");
            }
        }



            FileReaderHelper.writeLines(WORKSPACES_FILE, updated);
            System.out.println("Workspace removed successfully.");
        }

    static void viewAllReservations() {
        List<String> lines = FileReaderHelper.readLines(RESERVATIONS_FILE);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    static void viewAllWorkSpaces() {
        WorkspaceDAO dao = new WorkspaceDAO();
        try {
            List<WorkSpace> list = dao.getAvailableWorkspaces();
            if (list.isEmpty()) {
                System.out.println("No available workspaces found.");
            } else {
                for (WorkSpace ws : list) {
                    System.out.println(ws.toFileString());
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading workspaces: " + e.getMessage());
        }
    }


    // Customer: Browse available spaces
    static void browseSpaces() {
        List<String> lines = FileReaderHelper.readLines(WORKSPACES_FILE);
        for (String line : lines) {
            WorkSpace ws = WorkSpace.fromFileString(line);
            if (ws.available){
                System.out.println(line);
            }
        }
    }



    static void makeReservation(String name) {
        try {
            System.out.print("Enter workspace ID: ");
            String wid = scanner.nextLine();
            System.out.print("Enter date YYYY-MM-DD: ");
            String date = scanner.nextLine();
            System.out.print("Start time HH:MM: ");
            String start = scanner.nextLine();
            System.out.print("End time HH:MM: ");
            String end = scanner.nextLine();

            String id = "R" + new Random().nextInt(10000);
            Reservation r = new Reservation(id, name, wid, date, start, end);
            ReservationDAO dao = new ReservationDAO();
            dao.insertReservation(r);

            System.out.println("Reservation successful for workspace " + wid + " on " + date + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    static void viewUserReservations(String name) {
        try {
            ReservationDAO dao = new ReservationDAO();
            List<Reservation> reservations = dao.getUserReservations(name);

            if (reservations.isEmpty()) {
                System.out.println("No reservations found for " + name);
            } else {
                for (Reservation r : reservations) {
                    System.out.println(r);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving reservations: " + e.getMessage());
        }
    }


    static void cancelReservation(String name) {
        try {
            ReservationDAO dao = new ReservationDAO();
            int deleted = dao.cancelUserReservation(name);

            if (deleted > 0) {
                System.out.println("All your " + deleted + " reservations have been canceled.");
            } else {
                System.out.println("You had no reservations to cancel.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
