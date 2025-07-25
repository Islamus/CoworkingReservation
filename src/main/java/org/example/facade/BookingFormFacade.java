package org.example.facade;

import org.example.entities.Booking;
import org.example.repository.UserRepository;
import org.example.repository.WorkSpaceRepository;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class BookingFormFacade {

    private final UserRepository userRepository;
    private final WorkSpaceRepository workspaceRepository;

    public BookingFormFacade(UserRepository userRepository, WorkSpaceRepository workspaceRepository) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public void prepareForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("workspaces", workspaceRepository.findAll());
    }
}
