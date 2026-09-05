package com.hotel.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("notifications")
@Data
public class Notification {
    @Id
    private String id;
    private String bookingId;
    private String userEmail;
    private String message;
    private NotificationType type;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime sentAt;
}
