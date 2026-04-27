package com.hotel.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document("payments")
public class Payment {
    @Id
    private String id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datePaid;
    private String bookingId;
    private double amountPaid;
    private boolean isSuccessful;
}
