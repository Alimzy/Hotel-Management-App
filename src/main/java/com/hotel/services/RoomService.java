package com.hotel.services;

import com.hotel.data.models.Room;
import com.hotel.data.repositories.RoomRepository;
import com.hotel.dtos.requests.RoomRequest;
import com.hotel.dtos.responses.RoomResponse;
import com.hotel.exceptions.RoomAlreadyExistsException;
import com.hotel.exceptions.RoomNotFoundException;
import com.hotel.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public RoomResponse addRoom(RoomRequest request){

        Optional<Room> existingRoom = roomRepository.findByRoomNumber(request.getRoomNumber());
        if (existingRoom.isPresent()) {
            throw new RoomAlreadyExistsException("Room number already exists");
        }
        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setRoomNumber(request.getRoomNumber());

        Room savedRoom = roomRepository.save(room);


       return Mapper.map(savedRoom);
    }

    public RoomResponse findRoomByNumber(String roomNumber){

            Room room = roomRepository.findByRoomNumber(roomNumber)
                    .orElseThrow(() -> new RoomNotFoundException("Room not found"));
            RoomResponse response = new RoomResponse();


            return Mapper.map(room);

    }

    public List<RoomResponse> getAllRoom(){
        List<Room> allRooms =  roomRepository.findAll();
        ArrayList<RoomResponse>  myRoom = new ArrayList<>();
        for(Room room : allRooms){
            myRoom.add( Mapper.map(room));
        }

        return myRoom;
    }

    public String deleteRoom(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        roomRepository.delete(room);
        return "Room deleted successfully";
    }

    public List<RoomResponse> getAvailableRoom(){

        List<Room> allRooms =  roomRepository.findByAvailableTrue();
        ArrayList<RoomResponse>  myRoom = new ArrayList<>();
        for(Room room : allRooms){
            myRoom.add( Mapper.map(room));
        }

        return myRoom;

    }

}
