package com.example.collegeerp.model;

import com.example.collegeerp.model.enums.AttendanceStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;
    
    @Column(name = "class_time")
    private LocalTime classTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AttendanceStatus status;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "semester")
    private Integer semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    // Default constructor
    public Attendance() {}
    
    // Constructor
    public Attendance(Student student, Course course, Faculty faculty, LocalDate attendanceDate, AttendanceStatus status) {
        this.student = student;
        this.course = course;
        this.faculty = faculty;
        this.attendanceDate = attendanceDate;
        this.status = status;
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
    
    public Faculty getFaculty() {
        return faculty;
    }
    
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
    
    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
    
    public LocalTime getClassTime() {
        return classTime;
    }
    
    public void setClassTime(LocalTime classTime) {
        this.classTime = classTime;
    }
    
    public AttendanceStatus getStatus() {
        return status;
    }
    
    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}
