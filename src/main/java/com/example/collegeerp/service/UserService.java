package com.example.collegeerp.service;

import com.example.collegeerp.dto.request.SignupRequest;
import com.example.collegeerp.model.User;
import com.example.collegeerp.model.enums.Role;
import com.example.collegeerp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username.trim());
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    public User createUser(SignupRequest signUpRequest) {
        String trimmedUsername = signUpRequest.getUsername().trim();
        String trimmedEmail = signUpRequest.getEmail().trim().toLowerCase();
        String trimmedPassword = signUpRequest.getPassword().trim();
        String trimmedFirstName = signUpRequest.getFirstName().trim();
        String trimmedLastName = signUpRequest.getLastName().trim();

        User user = new User(trimmedUsername,
                trimmedEmail,
                encoder.encode(trimmedPassword),
                trimmedFirstName,
                trimmedLastName);

        if (signUpRequest.getPhone() != null && !signUpRequest.getPhone().trim().isEmpty()) {
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
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username.trim());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email.trim().toLowerCase());
    }

    public User updateLastLogin(String username) {
        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setLastLogin(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
}
