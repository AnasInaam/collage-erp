package com.example.springbootdemo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        // Sample data loading disabled for now
        System.out.println("College ERP Application started successfully!");
    }
}
