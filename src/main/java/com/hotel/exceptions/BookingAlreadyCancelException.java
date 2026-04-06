package com.hotel.exceptions;

public class BookingAlreadyCancelException extends RuntimeException{
    public BookingAlreadyCancelException(String message){
        super(message);
    }
}
