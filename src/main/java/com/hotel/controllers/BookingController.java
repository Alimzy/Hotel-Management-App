package com.hotel.controllers;

import com.hotel.dtos.requests.BookingRequest;
import com.hotel.dtos.requests.RoomRequest;
import com.hotel.dtos.responses.BookingResponse;
import com.hotel.dtos.responses.RoomResponse;
import com.hotel.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request){
        return bookingService.createBooking(request);
    }


    @GetMapping
    public List<BookingResponse> getAllBookings(){
        return bookingService.getAllBookings();

    }

    @GetMapping("/{id}")
    public BookingResponse getABookingById(@PathVariable String id){
        return bookingService.findBookingById(id);
    }

    @PatchMapping("/{id}")
    public String cancelBooking(@PathVariable String id){
        return bookingService.cancelBooking(id);
    }

}
