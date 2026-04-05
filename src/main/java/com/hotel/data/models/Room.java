package com.hotel.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("room")
@Data
public class Room {
    @Id
    private String id;
    private String roomNumber;
    private RoomType roomType;
    private boolean isAvailable = true;
}
