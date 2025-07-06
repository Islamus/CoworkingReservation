package org.example.services;

import org.example.dao.WorkSpaceDAO;
import org.example.entities.WorkSpace;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class WorkSpaceService {
    private final WorkSpaceDAO workspaceDAO;

    public WorkSpaceService(WorkSpaceDAO workspaceDAO) {
        this.workspaceDAO = workspaceDAO;
    }

    public void addWorkspace(Scanner scanner) {
        System.out.print("Enter workspace type (e.g., desk): ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        WorkSpace ws = new WorkSpace(type, price, true);
        workspaceDAO.create(ws);
        System.out.println("Workspace added successfully.");
    }

    public void removeWorkspace(Scanner scanner) {
        System.out.print("Enter workspace ID to remove: ");
        Long id = Long.parseLong(scanner.nextLine());
        workspaceDAO.deleteById(id);
        System.out.println("Workspace removed successfully.");
    }

    public void listAvailableWorkspaces() {
        List<WorkSpace> list = workspaceDAO.findAvailable();
        if (list.isEmpty()) {
            System.out.println("No available workspaces.");
        } else {
            for (WorkSpace ws : list) {
                System.out.printf("ID: %d, Type: %s, Price: %.2f%n",
                        ws.getId(), ws.getType(), ws.getPrice());
            }
        }
    }
}
