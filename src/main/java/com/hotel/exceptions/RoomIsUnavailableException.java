package com.hotel.exceptions;

public class RoomIsUnavailableException extends RuntimeException {

    public RoomIsUnavailableException(String message){
        super(message);
    }
}
