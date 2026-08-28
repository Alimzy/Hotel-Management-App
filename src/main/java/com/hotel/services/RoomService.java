package com.hotel.services;

import com.hotel.data.models.BookingStatus;
import com.hotel.data.models.Room;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.RoomRepository;
import com.hotel.dtos.requests.RoomRequest;
import com.hotel.dtos.responses.RoomResponse;
import com.hotel.exceptions.InvalidRoomDataException;
import com.hotel.exceptions.RoomAlreadyExistsException;
import com.hotel.exceptions.RoomHasActiveBookingsException;
import com.hotel.exceptions.RoomNotFoundException;
import com.hotel.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public RoomResponse addRoom(RoomRequest request){
        if (!StringUtils.hasText(request.getRoomNumber())) {
            throw new InvalidRoomDataException("Room number is required");
        }
        if (request.getRoomType() == null) {
            throw new InvalidRoomDataException("Room type is required");
        }
        String roomNumber = request.getRoomNumber().trim().toUpperCase();

        Optional<Room> existingRoom = roomRepository.findByRoomNumber(roomNumber);
        if (existingRoom.isPresent()) {
            throw new RoomAlreadyExistsException("Room number already exists");
        }

        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setRoomNumber(roomNumber);

        Room savedRoom = roomRepository.save(room);


       return Mapper.map(savedRoom);
    }

    public RoomResponse findRoomByNumber(String roomNumber){

        if (!StringUtils.hasText(roomNumber)) {
            throw new InvalidRoomDataException("Room number is required");
        }
        Room room = roomRepository.findByRoomNumber(roomNumber.trim().toUpperCase())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
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
        if (!StringUtils.hasText(roomNumber)) {
            throw new InvalidRoomDataException("Room number is required");
        }
        Room room = roomRepository.findByRoomNumber(roomNumber.trim().toUpperCase())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        boolean hasActiveBooking = bookingRepository
                .existsByRoomNumberAndBookingStatus(room.getRoomNumber(), BookingStatus.CONFIRMED);
        if (hasActiveBooking) {
            throw new RoomHasActiveBookingsException("Cannot delete a room with an active booking");
        }

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
