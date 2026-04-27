package com.hotel.exceptions;

public class BookingAlreadyPaidException extends RuntimeException {
    public BookingAlreadyPaidException(String message) {
        super(message);
    }
}
