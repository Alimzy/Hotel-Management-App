package com.hotel.services;

import com.hotel.data.models.Booking;
import com.hotel.data.models.BookingStatus;
import com.hotel.data.models.Room;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.RoomRepository;
import com.hotel.dtos.requests.BookingRequest;
import com.hotel.dtos.responses.BookingResponse;
import com.hotel.dtos.responses.RoomResponse;
import com.hotel.exceptions.*;
import com.hotel.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RoomRepository roomRepository;

    public BookingResponse createBooking(BookingRequest request){
        Room room = roomRepository.findByRoomNumber(request.getRoomNumber())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        if(!room.isAvailable()){
            throw new RoomIsUnavailableException("Room is unavailable, check another room");

        }

        double totalAmount = room.getRoomType().getPricePerNight() * request.getNumberOfNights();

        Booking booking = new Booking();
        booking.setRoomNumber(room.getRoomNumber());
        booking.setUserId(request.getUserId());
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
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));


       if( booking.getBookingStatus() == BookingStatus.CANCEL){
            throw new BookingAlreadyCancelException("Booking cancel already");
       }

        Room room = roomRepository.findByRoomNumber((booking.getRoomNumber()))
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        booking.setBookingStatus(BookingStatus.CANCEL);
        bookingRepository.save(booking);

       room.setAvailable(true);
       roomRepository.save(room);

       return "Booking cancel succesfully";
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
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        BookingResponse response = new BookingResponse();


        return Mapper.map(booking);
    }


}
