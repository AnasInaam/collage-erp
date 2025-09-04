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
        
        User user = userRepository.findByUsername(trimmedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + trimmedUsername));

        return UserPrincipal.create(user);
    }
}
