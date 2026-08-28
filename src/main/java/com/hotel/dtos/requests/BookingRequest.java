package com.hotel.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingRequest {
    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotBlank(message = "User email is required")
    private String userEmail;

    @NotNull(message = "Check-in date is required")
    @Future(message = "Check-in date must be in the future")
    private LocalDateTime checkInDate;

    @Min(value = 1, message = "Number of nights must be at least 1")
    private int numberOfNights;
}
