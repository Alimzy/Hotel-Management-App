package com.hotel.data.repositories;

import com.hotel.data.models.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GuestRepository extends MongoRepository<Guest, String> {
}
