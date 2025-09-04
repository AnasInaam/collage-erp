package com.example.collegeerp.controller;

import com.example.collegeerp.dto.request.LoginRequest;
import com.example.collegeerp.dto.request.SignupRequest;
import com.example.collegeerp.dto.response.JwtResponse;
import com.example.collegeerp.dto.response.MessageResponse;
import com.example.collegeerp.model.User;
import com.example.collegeerp.model.enums.Role;
import com.example.collegeerp.repository.UserRepository;
import com.example.collegeerp.security.JwtUtils;
import com.example.collegeerp.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    @Operation(summary = "Sign in user", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Trim username and password to handle any whitespace issues
            String trimmedUsername = loginRequest.getUsername().trim();
            String trimmedPassword = loginRequest.getPassword().trim();
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(trimmedUsername, trimmedPassword));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            // Update last login
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user != null) {
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
            }

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    roles));
        } catch (Exception e) {
            System.out.println("Failed to find user '" + loginRequest.getUsername() + "'");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Error: Invalid username or password!"));
        }
    }

    @PostMapping("/signup")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            // Trim all input fields to handle whitespace issues
            String trimmedUsername = signUpRequest.getUsername().trim();
            String trimmedEmail = signUpRequest.getEmail().trim();
            String trimmedPassword = signUpRequest.getPassword().trim();
            String trimmedFirstName = signUpRequest.getFirstName().trim();
            String trimmedLastName = signUpRequest.getLastName().trim();
            
            if (userRepository.existsByUsername(trimmedUsername)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(trimmedEmail)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            // Create new user's account
            User user = new User(trimmedUsername,
                    trimmedEmail,
                    encoder.encode(trimmedPassword),
                    trimmedFirstName,
                    trimmedLastName);

            if (signUpRequest.getPhone() != null) {
                user.setPhone(signUpRequest.getPhone().trim());
            }

            Set<Role> roles = new HashSet<>();
            
            if (signUpRequest.getRoles() == null || signUpRequest.getRoles().isEmpty()) {
                roles.add(Role.STUDENT);
            } else {
                signUpRequest.getRoles().forEach(role -> {
                    switch (role) {
                        case ADMIN:
                            roles.add(Role.ADMIN);
                            break;
                        case FACULTY:
                            roles.add(Role.FACULTY);
                            break;
                        case STAFF:
                            roles.add(Role.STAFF);
                            break;
                        case PARENT:
                            roles.add(Role.PARENT);
                            break;
                        case LIBRARIAN:
                            roles.add(Role.LIBRARIAN);
                            break;
                        case ACCOUNTANT:
                            roles.add(Role.ACCOUNTANT);
                            break;
                        default:
                            roles.add(Role.STUDENT);
                    }
                });
            }

            user.setRoles(roles);
            User savedUser = userRepository.save(user);
            
            System.out.println("User registered successfully: " + savedUser.getUsername());
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Registration failed. Please try again."));
        }
    }

    @PostMapping("/signout")
    @Operation(summary = "Sign out user", description = "Sign out current user")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("User signed out successfully!"));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get current authenticated user information")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("User not authenticated"));
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(null,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                roles));
    }

    @GetMapping("/verify")
    @Operation(summary = "Verify JWT token", description = "Verify if current JWT token is valid")
    public ResponseEntity<?> verifyToken(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(new MessageResponse("Token is valid"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid token"));
        }
    }
}
