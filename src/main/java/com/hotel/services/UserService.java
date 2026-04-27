package com.hotel.services;

import com.hotel.data.models.Role;
import com.hotel.data.models.User;
import com.hotel.data.repositories.UserRepository;
import com.hotel.dtos.requests.LoginRequest;
import com.hotel.dtos.requests.UserRequest;
import com.hotel.dtos.responses.LoginResponse;
import com.hotel.dtos.responses.UserResponse;
import com.hotel.exceptions.InvalidPasswordException;
import com.hotel.exceptions.UnauthorizedException;
import com.hotel.exceptions.UserAlreadyExistsException;
import com.hotel.exceptions.UserNotFoundException;
import com.hotel.mapper.Mapper;
import com.hotel.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

 public UserResponse registerUser(UserRequest request){
     Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
     if (existingUser.isPresent()) {
         throw new UserAlreadyExistsException("User already exists");
     }

     User user = new User();
      user.setEmail(request.getEmail());
      user.setName(request.getName());
     user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setPhoneNumber(request.getPhoneNumber());

      user.setRole(Role.GUEST);

      User savedUser = userRepository.save(user);


     return Mapper.userMap(savedUser);

 }

    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User cannot be found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return new LoginResponse(token, Mapper.userMap(user));
    }

    public List<UserResponse> getAllUsers(String token) {

        String email = jwtUtils.extractEmail(token);

        User requestingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        if (!requestingUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedException("Only admins can access this resource");
        }


        return userRepository.findAll()
                .stream()
                .map(Mapper::userMap)
                .toList();
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return Mapper.userMap(user);
    }


    public UserResponse updateUser(String email, UserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

        User updatedUser = userRepository.save(user);
        return Mapper.userMap(updatedUser);
    }


    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        return "User deleted successfully";
    }

    public UserResponse createAdmin(UserRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.ADMIN);

        return Mapper.userMap(userRepository.save(user));
    }

    public UserResponse updateUserRole(String email, Role role, String token) {
        String requesterEmail = jwtUtils.extractEmail(token);
        User requestingUser = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!requestingUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedException("Only admins can change roles");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setRole(role);
        return Mapper.userMap(userRepository.save(user));
    }
}
