package com.hotel.controllers;

import com.hotel.dtos.requests.LoginRequest;
import com.hotel.dtos.requests.UserRequest;
import com.hotel.dtos.responses.LoginResponse;
import com.hotel.dtos.responses.UserResponse;
import com.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse Login(@RequestBody LoginRequest request){
        return userService.loginUser(request);
    }

}
