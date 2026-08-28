package com.hotel.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("room")
@Data
public class Room {
    @Id
    private String id;
    @Indexed(unique = true)
    private String roomNumber;

    private RoomType roomType;

    private boolean available = true;

    @Version
    private Long version;
}
