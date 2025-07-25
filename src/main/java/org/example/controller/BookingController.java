package org.example.controller;

import org.example.entities.Booking;
import org.example.facade.BookingFormFacade;
import org.example.repository.BookingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingFormFacade bookingFormFacade;

    public BookingController(BookingRepository bookingRepository, BookingFormFacade bookingFormFacade) {
        this.bookingRepository = bookingRepository;
        this.bookingFormFacade = bookingFormFacade;
    }

    @GetMapping("/book")
    public String showBookingForm(Model model) {
        bookingFormFacade.prepareForm(model);
        return "booking_form";
    }

    @PostMapping("/book")
    public String processBooking(@ModelAttribute Booking booking) {
        bookingRepository.save(booking);
        return "redirect:/booking_list";
    }

    @GetMapping("/booking_list")
    public String showAllBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "booking_list";
    }
}
