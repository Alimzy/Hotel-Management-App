package com.hotel.services;

import com.hotel.data.repositories.RoomRepository;
import com.hotel.dtos.requests.RoomRequest;
import com.hotel.exceptions.RoomAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.hotel.data.models.RoomType.SUITE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @BeforeEach
    public void setUp(){
        roomRepository.deleteAll();
    }

    @Test
    public void testThatRoomCanBeCreated(){
        assertEquals(0,roomRepository.count());
        RoomRequest request = new RoomRequest();
        request.setRoomNumber("23");
        request.setRoomType(SUITE);
        roomService.addRoom(request);
        assertEquals(1,roomRepository.count());
    }

    @Test
    public void testThatRoomCannotCreateAlreadyExistRoom(){
        assertEquals(0,roomRepository.count());
        RoomRequest request = new RoomRequest();
        request.setRoomNumber("23");
        request.setRoomType(SUITE);
        roomService.addRoom(request);

        assertThrows(RoomAlreadyExistsException.class,() ->{
            roomService.addRoom(request);
        });
    }

}