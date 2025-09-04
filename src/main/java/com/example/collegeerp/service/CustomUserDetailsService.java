package com.example.collegeerp.service;

import com.example.collegeerp.model.User;
import com.example.collegeerp.repository.UserRepository;
import com.example.collegeerp.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Trim username to handle whitespace issues
        String trimmedUsername = username.trim();
        
        System.out.println("DEBUG: Looking for user with username: '" + trimmedUsername + "'");
        
        User user = userRepository.findByUsername(trimmedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + trimmedUsername));

        System.out.println("DEBUG: Found user: " + user.getUsername() + " with roles: " + user.getRoles());
        
        return UserPrincipal.create(user);
    }
}
