package org.example.services;

import org.example.dao.UserDAO;
import org.example.entities.User;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User createUser(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userDAO.create(user);
        return user;
    }
}