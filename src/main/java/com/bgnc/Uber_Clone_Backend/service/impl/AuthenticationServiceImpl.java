package com.bgnc.Uber_Clone_Backend.service.impl;

import com.bgnc.Uber_Clone_Backend.dto.AuthRequest;
import com.bgnc.Uber_Clone_Backend.dto.AuthResponse;
import com.bgnc.Uber_Clone_Backend.enums.Role;
import com.bgnc.Uber_Clone_Backend.exception.BaseException;
import com.bgnc.Uber_Clone_Backend.exception.ErrorMessage;
import com.bgnc.Uber_Clone_Backend.exception.MessageType;
import com.bgnc.Uber_Clone_Backend.jwt.JWTService;
import com.bgnc.Uber_Clone_Backend.model.Driver;
import com.bgnc.Uber_Clone_Backend.model.Passenger;
import com.bgnc.Uber_Clone_Backend.model.User;
import com.bgnc.Uber_Clone_Backend.repository.DriverRepository;
import com.bgnc.Uber_Clone_Backend.repository.PassengerRepository;
import com.bgnc.Uber_Clone_Backend.repository.UserRepository;
import com.bgnc.Uber_Clone_Backend.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    private User createUser(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        try {
            // Role'ü enum'a dönüştür
            Role role = Role.valueOf(authRequest.getRole().toUpperCase());
            user.setRoles(Set.of(role)); // Role'ü set olarak ayarlıyoruz
        } catch (IllegalArgumentException e) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ROLE, "Invalid role provided"));
        }

        return user;
    }

    @Override
    public AuthRequest register(AuthRequest authRequest) {
        String sanitizedUsername = authRequest.getUsername().trim().toLowerCase();

        // Kullanıcı adı kontrolü ve kullanıcı oluşturma
        if (userRepository.findByUsername(sanitizedUsername).isPresent()) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_ALREADY_EXISTS, "Username already exists"));
        }

        User newUser = createUser(authRequest);
        newUser.setUsername(sanitizedUsername);

        // Kullanıcının rolüne göre uygun repository'ye kaydetme
        if (newUser.getRoles().contains(Role.PASSENGER)) {
            // Passenger rolü için Passenger repository'ye kaydediyoruz
            passengerRepository.save((Passenger) newUser);
        } else if (newUser.getRoles().contains(Role.DRIVER)) {
            // Driver rolü için Driver repository'ye kaydediyoruz
            driverRepository.save((Driver) newUser);
        } else {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ROLE, "Role not found"));
        }

        // Yanıt oluştur
        return new AuthRequest(
                newUser.getUsername(),
                null, // Şifreyi istemciye göndermiyoruz
                newUser.getRoles().iterator().next().name() // Seçilen rolü yanıt olarak döndürüyoruz
        );
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_CREDENTIALS, "Invalid credentials"));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_CREDENTIALS, "Invalid credentials"));
        }

        Set<Role> roles = user.getRoles();
        boolean isPassenger = roles.contains(Role.PASSENGER);
        boolean isDriver = roles.contains(Role.DRIVER);

        if (isPassenger && isDriver) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ROLE, "A user cannot be both a driver and a passenger"));
        }

        String accessToken = jwtService.generateToken(user, roles);

        return new AuthResponse(accessToken);
    }

}