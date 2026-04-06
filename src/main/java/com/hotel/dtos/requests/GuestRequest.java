package com.hotel.dtos.requests;

import lombok.Data;

@Data
public class GuestRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
