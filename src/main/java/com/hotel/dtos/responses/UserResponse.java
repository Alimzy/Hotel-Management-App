package com.hotel.dtos.responses;

import com.hotel.data.models.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
}
