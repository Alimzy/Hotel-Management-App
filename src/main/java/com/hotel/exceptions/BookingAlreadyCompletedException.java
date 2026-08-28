package com.hotel.exceptions;

public class BookingAlreadyCompletedException extends RuntimeException {
    public BookingAlreadyCompletedException(String message) {
        super(message);
    }
}
