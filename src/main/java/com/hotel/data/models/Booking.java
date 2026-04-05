package com.hotel.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("bookings")
@Data
public class Booking {
    @Id
    private String id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String userId;
    private String roomId;
    private double totalAmount;
    private BookingStatus bookingStatus;
    private boolean paymentStatus;
}
