package com.example.collegeerp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "name", unique = true)
    private String name;
    
    @NotBlank
    @Size(max = 10)
    @Column(name = "code", unique = true)
    private String code;
    
    @Size(max = 500)
    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_of_department_id")
    private Faculty headOfDepartment;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Faculty> faculty = new HashSet<>();
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();
    
    // Default constructor
    public Department() {}
    
    // Constructor
    public Department(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Faculty getHeadOfDepartment() {
        return headOfDepartment;
    }
    
    public void setHeadOfDepartment(Faculty headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }
    
    public Set<Faculty> getFaculty() {
        return faculty;
    }
    
    public void setFaculty(Set<Faculty> faculty) {
        this.faculty = faculty;
    }
    
    public Set<Course> getCourses() {
        return courses;
    }
    
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
