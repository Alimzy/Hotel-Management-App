package com.hotel.controllers;

import com.hotel.dtos.requests.RoomRequest;
import com.hotel.dtos.responses.RoomResponse;
import com.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService ;


    @PostMapping
    public RoomResponse addRoom(@RequestBody RoomRequest request){
        return roomService.addRoom(request);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms(){
        return roomService.getAllRoom();

    }

    @GetMapping("/{roomNumber}")
    public RoomResponse getARoomByNumber(@PathVariable String roomNumber){
        return roomService.findRoomByNumber(roomNumber);
    }

    @DeleteMapping("/{roomNumber}")
    public String deleteRoom(@PathVariable String roomNumber){
        return roomService.deleteRoom(roomNumber);
    }

    @GetMapping("/available")
    public List<RoomResponse> getAvailableRoom() {
        return roomService.getAvailableRoom();
    }
}


