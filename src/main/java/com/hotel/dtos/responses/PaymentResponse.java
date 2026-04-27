package com.hotel.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private String id;
    private LocalDateTime datePaid;
    private String bookingId;
    private double amountPaid;
    private boolean isSuccessful;
}
