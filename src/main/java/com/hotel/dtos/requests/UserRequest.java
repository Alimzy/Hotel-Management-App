package com.hotel.dtos.requests;

import com.hotel.data.models.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    private String phoneNumber;
    private String password;
}
