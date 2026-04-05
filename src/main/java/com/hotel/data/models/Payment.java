package com.hotel.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document("payments")
public class Payment {
    @Id
    private String id;
    private LocalDateTime datePaid;
    private String bookingId;
    private double amountPaid;
    private boolean isSuccessful;
}
