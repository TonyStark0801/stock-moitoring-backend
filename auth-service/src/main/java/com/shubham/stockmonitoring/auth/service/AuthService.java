package com.shubham.stockmonitoring.auth.service;

import com.shubham.stockmonitoring.auth.dto.AuthResponse;
import com.shubham.stockmonitoring.auth.dto.LoginRequest;
import com.shubham.stockmonitoring.auth.dto.RegisterRequest;
import com.shubham.stockmonitoring.auth.entity.User;
import com.shubham.stockmonitoring.auth.repository.UserRepository;
import com.shubham.stockmonitoring.commons.exception.CustomException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        Optional<User> user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (user.isPresent()) {
            throw new CustomException("USER_EXISTS", "Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(newUser);
        
        // Generate JWT token
        String token = jwtService.generateToken(savedUser);
        
        return new AuthResponse(
            token,
            "Bearer",
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getRole().name()
        );
    }
    
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
        // Get user details
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new CustomException("USER_NOT_FOUND", "User not found"));
        
        // Generate JWT token
        String token = jwtService.generateToken(user);
        
        return new AuthResponse(
            token,
            "Bearer",
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );
    }
    
    public String validateToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new CustomException("INVALID_TOKEN", "Invalid token"));
        
        if (!jwtService.isTokenValid(token, user)) {
            throw new CustomException("INVALID_TOKEN", "Token is invalid or expired");
        }
        
        return user.getId().toString();
    }
}
