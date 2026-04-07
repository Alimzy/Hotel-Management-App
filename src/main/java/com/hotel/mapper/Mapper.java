package com.hotel.mapper;

import com.hotel.data.models.Booking;
import com.hotel.data.models.Room;
import com.hotel.dtos.responses.BookingResponse;
import com.hotel.dtos.responses.RoomResponse;

public class Mapper {
    public static RoomResponse map(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setPricePerNight(room.getRoomType().getPricePerNight());
        return response;
    }

    public static BookingResponse map(Booking booking, Room room, int numberOfNights) {
        BookingResponse response = new BookingResponse();
        response.setRoomNumber(room.getRoomNumber());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setUserId(booking.getUserId());
        response.setRoomId(booking.getRoomNumber());
        response.setNumberOfNights(numberOfNights);
        response.setTotalAmount(booking.getTotalAmount());
        response.setPaymentStatus(booking.isPaymentStatus());
        return response;
    }

    public static BookingResponse map(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setRoomNumber(booking.getRoomNumber());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setUserId(booking.getUserId());
        response.setRoomId(booking.getRoomNumber());
        response.setTotalAmount(booking.getTotalAmount());
        response.setPaymentStatus(booking.isPaymentStatus());
        response.setNumberOfNights(booking.getNumberOfNights());
        response.setId(booking.getId());

        return response;
    }
}
