package com.hotel.services;

import com.hotel.data.models.*;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.PaymentRepository;
import com.hotel.data.repositories.UserRepository;
import com.hotel.dtos.responses.PaymentResponse;
import com.hotel.exceptions.*;
import com.hotel.mapper.Mapper;
import com.hotel.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public PaymentResponse makePayment(String bookingId) {
        if (!StringUtils.hasText(bookingId)) {
            throw new InvalidPaymentDataException("Booking id is required");
        }
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.isPaymentStatus()) {
            throw new BookingAlreadyPaidException("Booking already paid for");
        }


        if (booking.getBookingStatus() == BookingStatus.CANCEL) {
            throw new BookingAlreadyCancelledException("Cannot pay for a cancelled booking");
        }

        booking.setPaymentStatus(true);
        try {
            bookingRepository.save(booking);
        } catch (OptimisticLockingFailureException e) {
            throw new BookingAlreadyPaidException("Booking was just paid for by another request");
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


    public List<PaymentResponse> getAllPayments(String token) {
            if (!StringUtils.hasText(token)) {
                throw new InvalidPaymentDataException("Token is required");
            }

            String email = jwtUtils.extractEmail(token);
            User requestingUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            if (!requestingUser.getRole().equals(Role.ADMIN)) {
                throw new UnauthorizedException("Only admins can view all payments");
            }
        List<Payment> allPayments = paymentRepository.findAll();
        ArrayList<PaymentResponse> myPayments = new ArrayList<>();
        for (Payment payment : allPayments) {
            myPayments.add(Mapper.map(payment));
        }
        return myPayments;
    }


    public List<PaymentResponse> getPaymentsByBookingId(String bookingId) {
        if (!StringUtils.hasText(bookingId)) {
            throw new InvalidPaymentDataException("Booking id is required");
        }
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        ArrayList<PaymentResponse> myPayments = new ArrayList<>();
        for (Payment payment : payments) {
            myPayments.add(Mapper.map(payment));
        }
        return myPayments;
    }

}
