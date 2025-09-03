package com.example.collegeerp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student_enrollments")
public class StudentEnrollment extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    
    @Column(name = "semester")
    private Integer semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    @Column(name = "grade")
    private String grade;
    
    @Column(name = "grade_points")
    private Double gradePoints;
    
    @Column(name = "is_completed")
    private Boolean isCompleted = false;
    
    @Column(name = "attendance_percentage")
    private Double attendancePercentage;
    
    // Default constructor
    public StudentEnrollment() {}
    
    // Constructor
    public StudentEnrollment(Student student, Course course, LocalDate enrollmentDate, Integer semester) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
    }
    
    // Getters and Setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public Integer getSemester() {
        return semester;
    }
    
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public Double getGradePoints() {
        return gradePoints;
    }
    
    public void setGradePoints(Double gradePoints) {
        this.gradePoints = gradePoints;
    }
    
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    public Double getAttendancePercentage() {
        return attendancePercentage;
    }
    
    public void setAttendancePercentage(Double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }
}
