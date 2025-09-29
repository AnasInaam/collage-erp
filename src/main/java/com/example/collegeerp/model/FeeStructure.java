package com.example.collegeerp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fee_structure")
public class FeeStructure extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @Column(name = "semester")
    private Integer semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    @NotNull
    @Positive
    @Column(name = "tuition_fee", precision = 10, scale = 2)
    private BigDecimal tuitionFee;
    
    @Column(name = "hostel_fee", precision = 10, scale = 2)
    private BigDecimal hostelFee;
    
    @Column(name = "library_fee", precision = 10, scale = 2)
    private BigDecimal libraryFee;
    
    @Column(name = "lab_fee", precision = 10, scale = 2)
    private BigDecimal labFee;
    
    @Column(name = "examination_fee", precision = 10, scale = 2)
    private BigDecimal examinationFee;
    
    @Column(name = "miscellaneous_fee", precision = 10, scale = 2)
    private BigDecimal miscellaneousFee;
    
    @Column(name = "total_fee", precision = 10, scale = 2)
    private BigDecimal totalFee;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    // Default constructor
    public FeeStructure() {}
    
    // Getters and Setters
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
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
    
    public BigDecimal getTuitionFee() {
        return tuitionFee;
    }
    
    public void setTuitionFee(BigDecimal tuitionFee) {
        this.tuitionFee = tuitionFee;
    }
    
    public BigDecimal getHostelFee() {
        return hostelFee;
    }
    
    public void setHostelFee(BigDecimal hostelFee) {
        this.hostelFee = hostelFee;
    }
    
    public BigDecimal getLibraryFee() {
        return libraryFee;
    }
    
    public void setLibraryFee(BigDecimal libraryFee) {
        this.libraryFee = libraryFee;
    }
    
    public BigDecimal getLabFee() {
        return labFee;
    }
    
    public void setLabFee(BigDecimal labFee) {
        this.labFee = labFee;
    }
    
    public BigDecimal getExaminationFee() {
        return examinationFee;
    }
    
    public void setExaminationFee(BigDecimal examinationFee) {
        this.examinationFee = examinationFee;
    }
    
    public BigDecimal getMiscellaneousFee() {
        return miscellaneousFee;
    }
    
    public void setMiscellaneousFee(BigDecimal miscellaneousFee) {
        this.miscellaneousFee = miscellaneousFee;
    }
    
    public BigDecimal getTotalFee() {
        return totalFee;
    }
    
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    // Calculate total fee
    public void calculateTotalFee() {
        this.totalFee = BigDecimal.ZERO
            .add(tuitionFee != null ? tuitionFee : BigDecimal.ZERO)
            .add(hostelFee != null ? hostelFee : BigDecimal.ZERO)
            .add(libraryFee != null ? libraryFee : BigDecimal.ZERO)
            .add(labFee != null ? labFee : BigDecimal.ZERO)
            .add(examinationFee != null ? examinationFee : BigDecimal.ZERO)
            .add(miscellaneousFee != null ? miscellaneousFee : BigDecimal.ZERO);
    }
}
