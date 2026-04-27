package com.hotel.controllers;

import com.hotel.data.models.Payment;
import com.hotel.dtos.responses.PaymentResponse;
import com.hotel.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")

public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay/{bookingId}")
    public PaymentResponse makePayment(@PathVariable String bookingId) {
        return paymentService.makePayment(bookingId);
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{bookingId}")
    public List<Payment> getPaymentsByBookingId(@PathVariable String bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }
}
