package com.hotel.exceptions;

public class InvalidNotificationDataException extends RuntimeException {
    public InvalidNotificationDataException(String message) {
        super(message);
    }
}
