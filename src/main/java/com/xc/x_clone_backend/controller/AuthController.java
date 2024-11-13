package com.xc.x_clone_backend.controller;

import com.xc.x_clone_backend.security.JwtTokenProvider;
import com.xc.x_clone_backend.security.LoginRequest;
import com.xc.x_clone_backend.security.JwtAuthenticationResponse;
import com.xc.x_clone_backend.user.User;
import com.xc.x_clone_backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") 
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    private List<String> validateRegistration(User user) {
        List<String> errors = new ArrayList<>();

        String username = user.getUsername().toLowerCase();
        user.setUsername(username);

        if (userService.getUserById(username) != null) {
            errors.add("Username is already taken!");
        }

        if (username.length() < 4 || username.length() > 15) {
            errors.add("Username must be between 4 and 15 characters");
        }

        if (!username.matches("^[A-Za-z0-9_]+$")) {
            errors.add("Username can only contain letters, numbers, and underscores");
        }

        String password = user.getPassword();
        if (password.length() < 4 || password.length() > 15) {
            errors.add("Password must be between 4 and 15 characters");
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*\\d.*")) {
            errors.add("Password must contain at least one number");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errors.add("Password must contain at least one special character");
        }

        return errors;
    }

    private ResponseEntity<?> createErrorResponse(List<String> errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        List<String> validationErrors = validateRegistration(user);
        if (!validationErrors.isEmpty()) {
            return createErrorResponse(validationErrors);
        }
        try {
            User result = userService.createUser(user);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return createErrorResponse(Arrays.asList(e.getMessage()));
        }
    }

    @PostMapping("/testone")
public ResponseEntity<?> testValidate(@RequestBody User user) {
    System.out.println("Received - Username: " + user.getUsername() + ", Password: " + user.getPassword());
    List<String> errors = validateRegistration(user);
    return ResponseEntity.ok(Map.of(
        "username", user.getUsername(),
        "passwordLength", user.getPassword().length(),
        "errors", errors
    ));
}

@PostMapping("/testtwo")
public ResponseEntity<?> registerUserr(@RequestBody User user) {
    System.out.println("Received - Username: " + user.getUsername() + ", Password: " + user.getPassword());
    try {
        List<String> validationErrors;
        try {
            validationErrors = validateRegistration(user);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", "Validation error: " + e.getMessage()));
        }

        if (!validationErrors.isEmpty()) {
            return createErrorResponse(validationErrors);
        }

        User result = userService.createUser(user);
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Error: " + e.getMessage()));
    }
}

    @PostMapping("/guest")
    public ResponseEntity<?> guestLogin() {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    "newestuser",
                    null,
                    new ArrayList<>()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("errors", "Guest login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername().toLowerCase();
            loginRequest.setUsername(username);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (BadCredentialsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("errors", "Invalid username or password");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
    
}