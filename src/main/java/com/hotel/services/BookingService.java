package com.hotel.services;

import com.hotel.data.models.Booking;
import com.hotel.data.models.BookingStatus;
import com.hotel.data.models.Room;
import com.hotel.data.models.User;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.RoomRepository;
import com.hotel.data.repositories.UserRepository;
import com.hotel.dtos.requests.BookingRequest;
import com.hotel.dtos.responses.BookingResponse;
import com.hotel.exceptions.*;
import com.hotel.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    public BookingResponse createBooking(BookingRequest request){

        if (!StringUtils.hasText(request.getUserEmail())) {
            throw new InvalidBookingDataException("User email is required");
        }
        if (!StringUtils.hasText(request.getRoomNumber())) {
            throw new InvalidBookingDataException("Room number is required");
        }
        if (request.getCheckInDate() == null) {
            throw new InvalidBookingDataException("Check-in date is required");
        }
        if (request.getCheckInDate().isBefore(LocalDateTime.now())) {
            throw new InvalidBookingDataException("Check-in date cannot be in the past");
        }
        if (request.getNumberOfNights() <= 0) {
            throw new InvalidBookingDataException("Number of nights must be at least 1");
        }
        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Room room = roomRepository.findByRoomNumber(request.getRoomNumber().trim().toUpperCase())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        if(!room.isAvailable()){
            throw new RoomIsUnavailableException("Room is unavailable, check another room");

        }

        double totalAmount = room.getRoomType().getPricePerNight() * request.getNumberOfNights();

        Booking booking = new Booking();
        booking.setRoomNumber(room.getRoomNumber());
        booking.setUserEmail(request.getUserEmail());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckInDate().plusDays(request.getNumberOfNights()));
        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(false);
        booking.setNumberOfNights(request.getNumberOfNights());



        bookingRepository.save(booking);
        room.setAvailable(false);
        roomRepository.save(room);



        return Mapper.map(booking, room, request.getNumberOfNights());

    }

    public String cancelBooking(String id){
        if (!StringUtils.hasText(id)) {
            throw new InvalidBookingDataException("Booking id is required");
        }
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));


       if( booking.getBookingStatus() == BookingStatus.CANCEL){
            throw new BookingAlreadyCancelException("Booking cancel already");
       }
        if( booking.getBookingStatus() == BookingStatus.COMPLETED){
            throw new BookingAlreadyCompletedException("Cannot cancel a completed booking");
        }
        Room room = roomRepository.findByRoomNumber((booking.getRoomNumber()))
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

       booking.setBookingStatus(BookingStatus.CANCEL);
       bookingRepository.save(booking);
       room.setAvailable(true);
       roomRepository.save(room);

       return "Booking cancel successfully";
    }

    public List<BookingResponse> getAllBookings(){
        List<Booking> allBookings =  bookingRepository.findAll();
        ArrayList<BookingResponse> myBookings = new ArrayList<>();
        for(Booking booking : allBookings){
            myBookings.add( Mapper.map(booking));
        }

        return myBookings;
    }

    public BookingResponse findBookingById(String id){
        if (!StringUtils.hasText(id)) {
            throw new InvalidBookingDataException("Booking id is required");
        }
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));


        return Mapper.map(booking);
    }


}
