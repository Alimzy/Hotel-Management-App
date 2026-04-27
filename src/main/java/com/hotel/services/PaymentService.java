package com.hotel.services;

import com.hotel.data.models.Booking;
import com.hotel.data.models.BookingStatus;
import com.hotel.data.models.Payment;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.PaymentRepository;
import com.hotel.dtos.responses.PaymentResponse;
import com.hotel.exceptions.BookingAlreadyCancelledException;
import com.hotel.exceptions.BookingAlreadyPaidException;
import com.hotel.exceptions.BookingNotFoundException;
import com.hotel.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public PaymentResponse makePayment(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.isPaymentStatus()) {
            throw new BookingAlreadyPaidException("Booking already paid for");
        }


        if (booking.getBookingStatus() == BookingStatus.CANCEL) {
            throw new BookingAlreadyCancelledException("Cannot pay for a cancelled booking");
        }


        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmountPaid(booking.getTotalAmount());
        payment.setDatePaid(LocalDateTime.now());
        payment.setSuccessful(true);

        paymentRepository.save(payment);

        booking.setPaymentStatus(true);
        bookingRepository.save(booking);

        return Mapper.map(payment);
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public List<Payment> getPaymentsByBookingId(String bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

}
