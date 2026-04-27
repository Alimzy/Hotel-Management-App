package com.hotel.controllers;

import com.hotel.data.models.Role;
import com.hotel.dtos.requests.LoginRequest;
import com.hotel.dtos.requests.UserRequest;
import com.hotel.dtos.responses.LoginResponse;
import com.hotel.dtos.responses.UserResponse;
import com.hotel.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse Login(@RequestBody LoginRequest request){
        return userService.loginUser(request);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(@RequestHeader("Authorization") String token) {
        String cleanToken = token.substring(7);
        return userService.getAllUsers(cleanToken);
    }

    @PostMapping("/create-admin")
    public UserResponse createAdmin(@RequestBody UserRequest request) {
        return userService.createAdmin(request);
    }

    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable String email){
        return userService.deleteUser(email);
    }

    @PutMapping("/{email}")
    public UserResponse updateUser(@PathVariable String email,@RequestBody UserRequest request ){
        return userService.updateUser(email,request);
    }

    @PutMapping("/role")
    public UserResponse updateUserRole(@RequestParam String email,
                                       @RequestParam Role role,
                                       @RequestHeader("Authorization") String token) {
        String cleanToken = token.substring(7);
        return userService.updateUserRole(email, role, cleanToken);
    }
}
