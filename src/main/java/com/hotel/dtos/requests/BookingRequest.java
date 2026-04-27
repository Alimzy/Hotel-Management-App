package com.hotel.dtos.requests;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Data
public class BookingRequest {
    private String roomNumber;
    private String userEmail;
    private LocalDateTime checkInDate;
    private int numberOfNights;
}
