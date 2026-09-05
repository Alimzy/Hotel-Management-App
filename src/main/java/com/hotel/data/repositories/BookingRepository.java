package com.hotel.data.repositories;

import com.hotel.data.models.Booking;
import com.hotel.data.models.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    boolean existsByRoomNumberAndBookingStatus(String roomNumber, BookingStatus bookingStatus);

    boolean existsByUserEmailAndBookingStatus(String email, BookingStatus bookingStatus);

    List<Booking> findByCheckInDateBetweenAndBookingStatus(
            LocalDateTime start, LocalDateTime end, BookingStatus status);
}
