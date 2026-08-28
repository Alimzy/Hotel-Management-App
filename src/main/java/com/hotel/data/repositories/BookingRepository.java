package com.hotel.data.repositories;

import com.hotel.data.models.Booking;
import com.hotel.data.models.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {

    boolean existsByRoomNumberAndBookingStatus(String roomNumber, BookingStatus bookingStatus);
}
