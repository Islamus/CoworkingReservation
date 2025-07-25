package org.example.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("bookingService")
public class BookingService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public String formatStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            return "";
        }
        return startTime.format(FORMATTER);
    }

    public String formatEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            return "";
        }
        return endTime.format(FORMATTER);
    }
}
