package com.hotel.dtos.responses;

import lombok.Data;


import java.time.LocalDateTime;
@Data
public class BookingResponse {
    private String id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String userId;
    private String roomId;
    private double totalAmount;
    private boolean paymentStatus;
}
