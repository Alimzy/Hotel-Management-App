package com.hotel.data.repositories;

import com.hotel.data.models.Notification;
import com.hotel.data.models.NotificationType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository <Notification, String>{
    List<Notification> findByBookingId(String bookingId);
    boolean existsByBookingIdAndType(String bookingId, NotificationType type);
}

