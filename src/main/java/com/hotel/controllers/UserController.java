package com.hotel.controllers;

import com.hotel.data.models.Role;
import com.hotel.dtos.requests.LoginRequest;
import com.hotel.dtos.requests.UserRequest;
import com.hotel.dtos.responses.LoginResponse;
import com.hotel.dtos.responses.UserResponse;
import com.hotel.exceptions.InvalidUserDataException;
import com.hotel.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LoginResponse Login(@Valid @RequestBody LoginRequest request){
        return userService.loginUser(request);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(@RequestHeader("Authorization") String token) {
        return userService.getAllUsers(extractToken(token));
    }

    @PostMapping("/create-admin")
    public UserResponse createAdmin(@Valid @RequestBody UserRequest request) {
        return userService.createAdmin(request);
    }

    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email, @RequestHeader("Authorization") String token){
        return userService.getUserByEmail(email,token);
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable String email, @RequestHeader("Authorization") String token){
        return userService.deleteUser(email,extractToken(token));
    }

    @PutMapping("/{email}")
    public UserResponse updateUser( @PathVariable String email,@RequestBody UserRequest request, @RequestHeader("Authorization") String token){
        return userService.updateUser(email,request,extractToken(token));
    }

    @PutMapping("/role")
    public UserResponse updateUserRole(@RequestParam String email,
                                       @RequestParam Role role,
                                       @RequestHeader("Authorization") String token) {
        return userService.updateUserRole(email, role, extractToken(token));
    }

    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidUserDataException("Missing or malformed Authorization header");
        }
        return authHeader.substring(7);
    }
}
