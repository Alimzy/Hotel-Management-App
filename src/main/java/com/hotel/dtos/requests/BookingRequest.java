package com.hotel.dtos.requests;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class BookingRequest {
    private String roomId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}
