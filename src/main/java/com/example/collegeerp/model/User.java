package com.example.collegeerp.model;

import com.example.collegeerp.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "email"),
           @UniqueConstraint(columnNames = "username")
       })
public class User extends BaseEntity {
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "username")
    private String username;
    
    @NotBlank
    @Size(max = 100)
    @Email
    @Column(name = "email")
    private String email;
    
    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    @Column(name = "password")
    private String password;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;
    
    @Size(max = 15)
    @Column(name = "phone")
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();
    
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;
    
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    
    @Column(name = "last_login")
    private java.time.LocalDateTime lastLogin;
    
    // Default constructor
    public User() {}
    
    // Constructor
    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }
    
    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }
    
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    
    public java.time.LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(java.time.LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
