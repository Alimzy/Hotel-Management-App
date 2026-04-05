package com.hotel.dtos.requests;

import com.hotel.data.models.RoomType;
import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private RoomType roomType;

}
