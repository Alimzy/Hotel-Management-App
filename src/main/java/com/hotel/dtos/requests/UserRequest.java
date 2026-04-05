package com.hotel.dtos.requests;

import com.hotel.data.models.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
