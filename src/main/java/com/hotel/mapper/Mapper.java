package com.hotel.mapper;

import com.hotel.data.models.Booking;
import com.hotel.data.models.Payment;
import com.hotel.data.models.Room;
import com.hotel.data.models.User;
import com.hotel.dtos.responses.*;

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
        response.setUserEmail(booking.getUserEmail());
        response.setRoomId(booking.getRoomNumber());
        response.setNumberOfNights(numberOfNights);
        response.setTotalAmount(booking.getTotalAmount());
        response.setPaymentStatus(booking.isPaymentStatus());
        response.setId(booking.getId());
        return response;
    }

    public static BookingResponse map(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setRoomNumber(booking.getRoomNumber());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setUserEmail(booking.getUserEmail());
        response.setRoomId(booking.getRoomNumber());
        response.setTotalAmount(booking.getTotalAmount());
        response.setPaymentStatus(booking.isPaymentStatus());
        response.setNumberOfNights(booking.getNumberOfNights());
        response.setId(booking.getId());

        return response;
    }

    public static UserResponse userMap(User user){
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());

        return response;

    }

    public static PaymentResponse map(Payment payment){
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setDatePaid(payment.getDatePaid());
        response.setAmountPaid(payment.getAmountPaid());
        response.setBookingId(payment.getBookingId());
        response.setSuccessful(payment.isSuccessful());

        return response;
    }

    public static NotificationResponse map(com.hotel.data.models.Notification notification){
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setBookingId(notification.getBookingId());
        response.setUserEmail(notification.getUserEmail());
        response.setMessage(notification.getMessage());
       response.setType(notification.getType().toString());
        response.setSentAt(notification.getSentAt());
        
        return response;
    }
}
