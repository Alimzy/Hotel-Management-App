package com.hotel.dtos.responses;

import com.hotel.data.models.Role;
import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
}
