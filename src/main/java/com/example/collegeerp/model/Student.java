package com.example.collegeerp.model;

import com.example.collegeerp.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends BaseEntity {
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @NotBlank
    @Size(max = 20)
    @Column(name = "roll_number", unique = true)
    private String rollNumber;
    
    @NotBlank
    @Size(max = 20)
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @Column(name = "admission_date")
    private LocalDate admissionDate;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @Size(max = 500)
    @Column(name = "address")
    private String address;
    
    @Size(max = 100)
    @Column(name = "parent_name")
    private String parentName;
    
    @Size(max = 15)
    @Column(name = "parent_phone")
    private String parentPhone;
    
    @Size(max = 100)
    @Column(name = "parent_email")
    private String parentEmail;
    
    @Column(name = "current_semester")
    private Integer currentSemester;
    
    @Column(name = "cgpa")
    private Double cgpa;
    
    @Column(name = "total_credits")
    private Integer totalCredits;
    
    @Column(name = "is_scholarship_holder")
    private Boolean isScholarshipHolder = false;
    
    @Size(max = 100)
    @Column(name = "blood_group")
    private String bloodGroup;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentEnrollment> enrollments = new HashSet<>();
    
    // Default constructor
    public Student() {}
    
    // Constructor
    public Student(User user, String rollNumber, String registrationNumber, Department department) {
        this.user = user;
        this.rollNumber = rollNumber;
        this.registrationNumber = registrationNumber;
        this.department = department;
    }
    
    // Getters and Setters
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getRollNumber() {
        return rollNumber;
    }
    
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public LocalDate getAdmissionDate() {
        return admissionDate;
    }
    
    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getParentName() {
        return parentName;
    }
    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    public String getParentPhone() {
        return parentPhone;
    }
    
    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }
    
    public String getParentEmail() {
        return parentEmail;
    }
    
    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }
    
    public Integer getCurrentSemester() {
        return currentSemester;
    }
    
    public void setCurrentSemester(Integer currentSemester) {
        this.currentSemester = currentSemester;
    }
    
    public Double getCgpa() {
        return cgpa;
    }
    
    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }
    
    public Integer getTotalCredits() {
        return totalCredits;
    }
    
    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }
    
    public Boolean getIsScholarshipHolder() {
        return isScholarshipHolder;
    }
    
    public void setIsScholarshipHolder(Boolean isScholarshipHolder) {
        this.isScholarshipHolder = isScholarshipHolder;
    }
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public Set<StudentEnrollment> getEnrollments() {
        return enrollments;
    }
    
    public void setEnrollments(Set<StudentEnrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
