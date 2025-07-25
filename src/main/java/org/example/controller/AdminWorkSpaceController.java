package org.example.controller;

import org.example.entities.WorkSpace;
import org.example.services.WorkSpaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/workspaces")
public class AdminWorkSpaceController {

    private final WorkSpaceService workSpaceService;

    public AdminWorkSpaceController(WorkSpaceService workSpaceService) {
        this.workSpaceService = workSpaceService;
    }

    @GetMapping
    public String adminWorkspaces(Model model) {
        model.addAttribute("workspaces", workSpaceService.findAll());
        model.addAttribute("workspace", new WorkSpace());
        return "admin_workspaces";
    }

    @PostMapping
    public String addWorkSpace(@ModelAttribute("workspace") WorkSpace workspace) {
        workSpaceService.save(workspace);
        return "redirect:/admin/workspaces";
    }
}
