package org.example;

import org.example.entities.User;
import org.example.services.BookingService;
import org.example.services.UserService;
import org.example.services.WorkSpaceService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example")) {
            Scanner scanner = new Scanner(System.in);
            UserService userService = context.getBean(UserService.class);
            WorkSpaceService workspaceService = context.getBean(WorkSpaceService.class);
            BookingService bookingService = context.getBean(BookingService.class);

            while (true) {
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. Admin Panel");
                System.out.println("2. Customer Panel");
                System.out.println("3. Exit");
                System.out.print("Select option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        adminMenu(scanner, workspaceService);
                        break;
                    case "2":
                        customerFlow(scanner, userService, workspaceService, bookingService);
                        break;
                    case "3":
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }

    private static void adminMenu(Scanner scanner, WorkSpaceService workspaceService) {
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
                    workspaceService.addWorkspace(scanner);
                    break;
                case "2":
                    workspaceService.removeWorkspace(scanner);
                    break;
                case "3":
                    workspaceService.listAvailableWorkspaces();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void customerFlow(Scanner scanner, UserService userService,
                                     WorkSpaceService workspaceService, BookingService bookingService) {
        User user = userService.createUser(scanner);

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
                    workspaceService.listAvailableWorkspaces();
                    break;
                case "2":
                    bookingService.makeBooking(scanner, user);
                    break;
                case "3":
                    bookingService.viewMyBookings(user);
                    break;
                case "4":
                    bookingService.cancelBooking(scanner, user);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
