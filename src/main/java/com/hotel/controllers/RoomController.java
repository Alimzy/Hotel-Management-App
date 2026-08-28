package com.hotel.controllers;

import com.hotel.dtos.requests.RoomRequest;
import com.hotel.dtos.responses.RoomResponse;
import com.hotel.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService ;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse addRoom(@Valid @RequestBody RoomRequest request){
        return roomService.addRoom(request);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms(){
        return roomService.getAllRoom();

    }

    @GetMapping("/available")
    public List<RoomResponse> getAvailableRoom() {
        return roomService.getAvailableRoom();
    }

    @GetMapping("/{roomNumber}")
    public RoomResponse getARoomByNumber(@PathVariable String roomNumber){
        return roomService.findRoomByNumber(roomNumber);
    }

    @DeleteMapping("/{roomNumber}")
    public String deleteRoom(@PathVariable String roomNumber){
        return roomService.deleteRoom(roomNumber);
    }


}


