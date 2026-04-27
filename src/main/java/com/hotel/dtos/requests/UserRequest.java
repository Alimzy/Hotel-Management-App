package com.hotel.dtos.requests;

import com.hotel.data.models.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    @Email(messa
}
