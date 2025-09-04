package com.example.springbootdemo.config;

import com.example.collegeerp.model.User;
import com.example.collegeerp.model.enums.Role;
import com.example.collegeerp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample users for testing
        if (userRepository.count() == 0) {
            // Create admin user
            User admin = new User("admin", "admin@college.edu", 
                passwordEncoder.encode("admin123"), "Admin", "User");
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setIsActive(true);
            admin.setIsEmailVerified(true);
            userRepository.save(admin);
            
            // Create student user
            User student = new User("student", "student@college.edu", 
                passwordEncoder.encode("student123"), "John", "Doe");
            student.setRoles(Set.of(Role.STUDENT));
            student.setIsActive(true);
            student.setIsEmailVerified(true);
            userRepository.save(student);
            
            // Create faculty user
            User faculty = new User("faculty", "faculty@college.edu", 
                passwordEncoder.encode("faculty123"), "Jane", "Smith");
            faculty.setRoles(Set.of(Role.FACULTY));
            faculty.setIsActive(true);
            faculty.setIsEmailVerified(true);
            userRepository.save(faculty);
            
            System.out.println("Sample users created:");
            System.out.println("Admin: username=admin, password=admin123");
            System.out.println("Student: username=student, password=student123");
            System.out.println("Faculty: username=faculty, password=faculty123");
        }
        
        System.out.println("College ERP Application started successfully!");
    }
}
