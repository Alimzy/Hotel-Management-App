package com.hotel.data.repositories;

import com.hotel.data.models.Payment;
import com.hotel.services.PaymentService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment,String> {
    List<Payment> findByBookingId(String bookingId);
}
