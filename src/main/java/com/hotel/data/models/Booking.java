package com.hotel.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("bookings")
@Data
public class Booking {
    @Id
    private String id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime checkInDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime checkOutDate;
    private String userEmail;
    private String roomNumber;
    private double totalAmount;
    private BookingStatus bookingStatus;
    private boolean paymentStatus;
    private int numberOfNights;

    @Version
    private Long version;
}
