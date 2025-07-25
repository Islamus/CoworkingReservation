package org.example.controller;

import org.example.entities.WorkSpace;
import org.example.services.WorkSpaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workspaces")
public class WorkSpaceController {

    private final WorkSpaceService workspaceService;

    public WorkSpaceController(WorkSpaceService workspaceService) {
        this.workspaceService = workspaceService;
    }


    @GetMapping
    public String showWorkspaces(Model model) {
        model.addAttribute("workspaces", workspaceService.findAll());
        model.addAttribute("workspace", new WorkSpace());
        return "workspaces";
    }

    @PostMapping
    public String addWorkspace(@ModelAttribute WorkSpace workspace) {
        workspaceService.save(workspace);
        return "redirect:/workspaces";
    }
}
