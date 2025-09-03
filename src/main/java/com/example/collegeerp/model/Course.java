package com.example.collegeerp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
    
    @NotBlank
    @Size(max = 10)
    @Column(name = "code", unique = true)
    private String code;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    
    @Size(max = 500)
    @Column(name = "description")
    private String description;
    
    @Min(1)
    @Max(10)
    @Column(name = "credits")
    private Integer credits;
    
    @Column(name = "duration_hours")
    private Integer durationHours;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    
    @Column(name = "semester")
    private Integer semester;
    
    @Column(name = "is_elective")
    private Boolean isElective = false;
    
    @Size(max = 500)
    @Column(name = "prerequisites")
    private String prerequisites;
    
    @Size(max = 1000)
    @Column(name = "syllabus")
    private String syllabus;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentEnrollment> enrollments = new HashSet<>();
    
    // Default constructor
    public Course() {}
    
    // Constructor
    public Course(String code, String name, Integer credits, Department department, Faculty faculty) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.department = department;
        this.faculty = faculty;
    }
    
    // Getters and Setters
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getCredits() {
        return credits;
    }
    
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    
    public Integer getDurationHours() {
        return durationHours;
    }
    
    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Faculty getFaculty() {
        return faculty;
    }
    
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    public Integer getSemester() {
        return semester;
    }
    
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public Boolean getIsElective() {
        return isElective;
    }
    
    public void setIsElective(Boolean isElective) {
        this.isElective = isElective;
    }
    
    public String getPrerequisites() {
        return prerequisites;
    }
    
    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }
    
    public String getSyllabus() {
        return syllabus;
    }
    
    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
    
    public Set<StudentEnrollment> getEnrollments() {
        return enrollments;
    }
    
    public void setEnrollments(Set<StudentEnrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
