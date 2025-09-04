package com.example.collegeerp.controller;

import com.example.collegeerp.dto.response.MessageResponse;
import com.example.collegeerp.model.User;
import com.example.collegeerp.security.UserPrincipal;
import com.example.collegeerp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "User profile and management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Get current user's profile information")
    public ResponseEntity<Map<String, Object>> getUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "User not authenticated");
            return ResponseEntity.status(401).body(error);
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<User> userOpt = userService.findByUsername(userPrincipal.getUsername());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> profile = new HashMap<>();
            profile.put("id", user.getId());
            profile.put("username", user.getUsername());
            profile.put("email", user.getEmail());
            profile.put("firstName", user.getFirstName());
            profile.put("lastName", user.getLastName());
            profile.put("phone", user.getPhone());
            profile.put("roles", user.getRoles());
            profile.put("lastLogin", user.getLastLogin());
            profile.put("isEmailVerified", user.getIsEmailVerified());
            profile.put("createdAt", user.getCreatedDate());
            profile.put("updatedAt", user.getLastModifiedDate());
            
            return ResponseEntity.ok(profile);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "User not found");
            return ResponseEntity.status(404).body(error);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Get all users (Admin only)")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // This would be implemented with pagination in a real application
            long totalUsers = userService.getTotalUsers();
            
            Map<String, Object> response = new HashMap<>();
            response.put("totalUsers", totalUsers);
            response.put("page", page);
            response.put("size", size);
            response.put("message", "User management endpoint available");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve users: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile", description = "Update current user's profile")
    public ResponseEntity<MessageResponse> updateUserProfile(
            @Valid @RequestBody Map<String, String> updates,
            Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401)
                    .body(new MessageResponse("User not authenticated"));
        }

        try {
            // This would be implemented with proper validation and update logic
            // For now, just return a placeholder message
            
            return ResponseEntity.ok(new MessageResponse("Profile update feature coming soon"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new MessageResponse("Failed to update profile: " + e.getMessage()));
        }
    }
}
