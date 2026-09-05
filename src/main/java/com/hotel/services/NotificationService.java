package com.hotel.services;

import com.hotel.data.models.Booking;
import com.hotel.data.models.BookingStatus;
import com.hotel.data.models.Notification;
import com.hotel.data.models.NotificationType;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.NotificationRepository;
import com.hotel.dtos.responses.NotificationResponse;
import com.hotel.exceptions.InvalidNotificationDataException;
import com.hotel.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailService emailService;

    public void sendBookingConfirmation(Booking booking) {
        String message = "Dear guest, your booking at CodexHotel is confirmed.\n"
                + "Room Number: " + booking.getRoomNumber() + "\n"
                + "Check-in Date: " + booking.getCheckInDate() + "\n"
                + "We look forward to hosting you!";

        sendAndSave(booking, NotificationType.BOOKING_CONFIRMATION, message);
    }

    @Scheduled(cron = "0 0 8 * * *") // runs daily at 8:00 AM
    public void sendCheckInReminders() {
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1).minusSeconds(1);

        List<Booking> upcomingBookings = bookingRepository
                .findByCheckInDateBetweenAndBookingStatus(startOfTomorrow, endOfTomorrow, BookingStatus.CONFIRMED);

        for (Booking booking : upcomingBookings) {
            boolean alreadyReminded = notificationRepository
                    .existsByBookingIdAndType(booking.getId(), NotificationType.CHECKIN_REMINDER);
            if (alreadyReminded) {
                continue;
            }

            String message = "Dear guest, this is a reminder for your stay at CodexHotel.\n"
                    + "Check-in Date: " + booking.getCheckInDate() + "\n"
                    + "Room Number: " + booking.getRoomNumber() + "\n"
                    + "We look forward to hosting you!";

            sendAndSave(booking, NotificationType.CHECKIN_REMINDER, message);
        }
    }

    private void sendAndSave(Booking booking, NotificationType type, String message) {
        try {
            emailService.sendEmail(booking.getUserEmail(), "CodexHotel Notification", message);
        } catch (Exception e) {

        }

        Notification notification = new Notification();
        notification.setBookingId(booking.getId());
        notification.setUserEmail(booking.getUserEmail());
        notification.setMessage(message);
        notification.setType(type);
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getNotificationsByBookingId(String bookingId) {
        if (!StringUtils.hasText(bookingId)) {
            throw new InvalidNotificationDataException("Booking id is required");
        }
        List<Notification> notifications = notificationRepository.findByBookingId(bookingId);
        ArrayList<NotificationResponse> result = new ArrayList<>();
        for (Notification notification : notifications) {
            result.add(Mapper.map(notification));
        }
        return result;
    }
}
