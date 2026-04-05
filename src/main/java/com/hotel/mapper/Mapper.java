package com.hotel.mapper;

import com.hotel.data.models.Room;
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
}
