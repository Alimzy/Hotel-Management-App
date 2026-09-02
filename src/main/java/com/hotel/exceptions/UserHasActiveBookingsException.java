package com.hotel.exceptions;

public class UserHasActiveBookingsException extends RuntimeException {
    public UserHasActiveBookingsException(String message) {
        super(message);
    }
}
