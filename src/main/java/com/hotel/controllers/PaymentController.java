package com.hotel.controllers;

import com.hotel.data.models.Payment;
import com.hotel.dtos.responses.PaymentResponse;
import com.hotel.exceptions.InvalidPaymentDataException;
import com.hotel.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")

public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay/{bookingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse makePayment(@PathVariable String bookingId) {
        return paymentService.makePayment(bookingId);
    }

    @GetMapping("/all")
    public List<PaymentResponse> getAllPayments(@RequestHeader("Authorization") String token) {
        return paymentService.getAllPayments(extractToken(token));
    }

    @GetMapping("/{bookingId}")
    public List<PaymentResponse> getPaymentsByBookingId(@PathVariable String bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }

    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidPaymentDataException("Missing or malformed Authorization header");
        }
        return authHeader.substring(7);
    }
}
