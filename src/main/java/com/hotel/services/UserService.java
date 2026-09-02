package com.hotel.services;

import com.hotel.data.models.BookingStatus;
import com.hotel.data.models.Role;
import com.hotel.data.models.User;
import com.hotel.data.repositories.BookingRepository;
import com.hotel.data.repositories.UserRepository;
import com.hotel.dtos.requests.LoginRequest;
import com.hotel.dtos.requests.UserRequest;
import com.hotel.dtos.responses.LoginResponse;
import com.hotel.dtos.responses.UserResponse;
import com.hotel.exceptions.*;
import com.hotel.mapper.Mapper;
import com.hotel.security.JwtUtils;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

 public UserResponse registerUser(UserRequest request){
     if (!StringUtils.hasText(request.getEmail())) {
         throw new InvalidUserDataException("Email is required");
     }
     if (!StringUtils.hasText(request.getName())) {
         throw new InvalidUserDataException("Name is required");
     }
     if (!StringUtils.hasText(request.getPassword())) {
         throw new InvalidUserDataException("Password is required");
     }

     String email = request.getEmail().trim().toLowerCase();
     Optional<User> existingUser = userRepository.findByEmail(email);
     if (existingUser.isPresent()) {
         throw new UserAlreadyExistsException("User already exists");
     }

     User user = new User();
      user.setEmail(request.getEmail());
      user.setName(request.getName());
     user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setPhoneNumber(request.getPhoneNumber());

      user.setRole(Role.GUEST);

     try {
         User savedUser = userRepository.save(user);
         return Mapper.userMap(savedUser);
     } catch (DuplicateKeyException e) {
         throw new UserAlreadyExistsException("User already exists");
     }

 }

    public LoginResponse loginUser(LoginRequest request) {

        if (!StringUtils.hasText(request.getEmail())) {
            throw new InvalidUserDataException("Email is required");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new InvalidUserDataException("Password is required");
        }

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }


        String token = jwtUtils.generateToken(user.getEmail());

        return new LoginResponse(token, Mapper.userMap(user));
    }

    public List<UserResponse> getAllUsers(String token) {
        if (!StringUtils.hasText(token)) {
            throw new InvalidUserDataException("Token is required");
        }

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

    public UserResponse getUserByEmail(String email,String token) {
        if (!StringUtils.hasText(email)) {
            throw new InvalidUserDataException("Email is required");
        }
        if (!StringUtils.hasText(token)) {
            throw new InvalidUserDataException("Token is required");
        }

        String normalizedEmail = email.trim().toLowerCase();
        String requesterEmail = jwtUtils.extractEmail(token);

        User requestingUser = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isSelf = requestingUser.getEmail().equalsIgnoreCase(normalizedEmail);
        boolean isAdmin = requestingUser.getRole().equals(Role.ADMIN);

        if (!isSelf && !isAdmin) {
            throw new UnauthorizedException("You can only view your own profile");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return Mapper.userMap(user);
    }


    public UserResponse updateUser(String email, UserRequest request,String token) {

        if (!StringUtils.hasText(email)) {
            throw new InvalidUserDataException("Email is required");
        }
        if (!StringUtils.hasText(token)) {
            throw new InvalidUserDataException("Token is required");
        }

        String normalizedEmail = email.trim().toLowerCase();
        String requesterEmail = jwtUtils.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

        User updatedUser = userRepository.save(user);
        return Mapper.userMap(updatedUser);
    }


    public String deleteUser(String email, String token) {
        if (!StringUtils.hasText(email)) {
            throw new InvalidUserDataException("Email is required");
        }
        if (!StringUtils.hasText(token)) {
            throw new InvalidUserDataException("Token is required");
        }

        String normalizedEmail = email.trim().toLowerCase();
        String requesterEmail = jwtUtils.extractEmail(token);

        User requestingUser = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isSelf = requestingUser.getEmail().equalsIgnoreCase(normalizedEmail);
        boolean isAdmin = requestingUser.getRole().equals(Role.ADMIN);

        if (!isSelf && !isAdmin) {
            throw new UnauthorizedException("You can only delete your own account");
        }

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean hasActiveBooking = bookingRepository
                .existsByUserEmailAndBookingStatus(user.getEmail(), BookingStatus.CONFIRMED);
        if (hasActiveBooking) {
            throw new UserHasActiveBookingsException("Cannot delete account with an active booking");
        }

        userRepository.delete(user);
        return "User deleted successfully";
    }

    public UserResponse createAdmin(UserRequest request) {
        if (!StringUtils.hasText(request.getEmail())) {
            throw new InvalidUserDataException("Email is required");
        }
        if (!StringUtils.hasText(request.getName())) {
            throw new InvalidUserDataException("Name is required");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new InvalidUserDataException("Password is required");
        }

        String email = request.getEmail().trim().toLowerCase();

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.ADMIN);
        try {
            User savedUser = userRepository.save(user);
            return Mapper.userMap(savedUser);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException("User already exists");
        }
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
