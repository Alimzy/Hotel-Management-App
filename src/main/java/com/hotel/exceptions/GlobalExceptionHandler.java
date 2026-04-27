package com.hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return error(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPassword(InvalidPasswordException ex) {
        return error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(UnauthorizedException ex) {
        return error(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoomNotFound(RoomNotFoundException ex) {
        return error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRoomAlreadyExists(RoomAlreadyExistsException ex) {
        return error(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoomIsUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleRoomUnavailable(RoomIsUnavailableException ex) {
        return error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBookingNotFound(BookingNotFoundException ex) {
        return error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingAlreadyCancelException.class)
    public ResponseEntity<Map<String, String>> handleBookingAlreadyCancelled(BookingAlreadyCancelException ex) {
        return error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingAlreadyCancelledException.class)
    public ResponseEntity<Map<String, String>> handleBookingCancelled(BookingAlreadyCancelledException ex) {
        return error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingAlreadyPaidException.class)
    public ResponseEntity<Map<String, String>> handleBookingAlreadyPaid(BookingAlreadyPaidException ex) {
        return error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Fallback for anything unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        return error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> error(String message, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("error", message);
        return new ResponseEntity<>(body, status);
    }
}