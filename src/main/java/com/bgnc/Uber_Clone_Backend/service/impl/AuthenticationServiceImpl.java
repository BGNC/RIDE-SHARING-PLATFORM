package com.bgnc.Uber_Clone_Backend.service.impl;

import com.bgnc.Uber_Clone_Backend.dto.AuthRequest;
import com.bgnc.Uber_Clone_Backend.dto.AuthResponse;
import com.bgnc.Uber_Clone_Backend.exception.BaseException;
import com.bgnc.Uber_Clone_Backend.exception.ErrorMessage;
import com.bgnc.Uber_Clone_Backend.exception.MessageType;
import com.bgnc.Uber_Clone_Backend.jwt.JWTService;
import com.bgnc.Uber_Clone_Backend.model.User;
import com.bgnc.Uber_Clone_Backend.repository.UserRepository;
import com.bgnc.Uber_Clone_Backend.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JWTService jwtService;


    private User createUser(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword())); // Encrypt password before saving
        return user;
    }

    @Override
    public AuthRequest register(AuthRequest authRequest) {
        try {
            // Check if the username already exists in the repository
            Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());
            if (optionalUser.isPresent()) {
                // Throw a custom exception if username already exists
                throw new BaseException(new ErrorMessage(MessageType.USERNAME_ALREADY_EXISTS, "Username already exists"));
            }

            // Save the user to the repository
            User savedUser = userRepository.save(createUser(authRequest));

            // Prepare and return the AuthRequest response (without password)
            AuthRequest response = new AuthRequest();
            response.setUsername(savedUser.getUsername());
            // If you need to set other properties, you can do so here.
            return response;


        } catch (Exception e) {
            // Handle any unexpected errors (e.g., database connection issues)
            throw new BaseException(new ErrorMessage(MessageType.SERVER_ERROR, "An error occurred during registration"));
        }
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        return null; // You can implement the authenticate logic here
    }
}