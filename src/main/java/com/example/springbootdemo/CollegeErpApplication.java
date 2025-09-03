package com.example.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.collegeerp.model"})
@EnableJpaRepositories(basePackages = {"com.example.collegeerp.repository"})
@ComponentScan(basePackages = {"com.example.springbootdemo", "com.example.collegeerp"})
public class CollegeErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeErpApplication.class, args);
    }
}
