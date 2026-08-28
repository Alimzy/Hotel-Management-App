package com.hotel.exceptions;

public class RoomHasActiveBookingsException extends RuntimeException {
    public RoomHasActiveBookingsException(String message) {
        super(message);
    }
}
