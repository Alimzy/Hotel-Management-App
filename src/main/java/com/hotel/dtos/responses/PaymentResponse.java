package com.hotel.dtos.responses;

import java.time.LocalDateTime;

public class PaymentResponse {
    private String id;
    private LocalDateTime datePaid;
    private String bookingId;
    private double amountPaid;
    private boolean isSuccessful;
}
