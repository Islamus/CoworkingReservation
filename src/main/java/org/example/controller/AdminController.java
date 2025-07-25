package org.example.controller;

import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showAdminForm(Model model) {
        model.addAttribute("user", new User());
        return "create_admin";
    }

    @PostMapping("/create")
    public String processAdminCreation(@ModelAttribute("user") User user) {
        userService.registerAdmin(user);
        return "redirect:/login";
    }
}
