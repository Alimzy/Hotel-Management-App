package com.hotel.dtos.responses;

import com.hotel.data.models.RoomType;
import lombok.Data;

@Data
public class RoomResponse {
    private String id;
    private String roomNumber;
    private RoomType roomType;
    private double pricePerNight;
}
