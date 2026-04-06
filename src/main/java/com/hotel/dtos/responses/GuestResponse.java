package com.hotel.dtos.responses;

import lombok.Data;

@Data
public class GuestResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
}
