package com.example.collegeerp.model;

import com.example.collegeerp.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payments")
public class FeePayment extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_structure_id")
    private FeeStructure feeStructure;
    
    @Column(name = "receipt_number", unique = true)
    private String receiptNumber;
    
    @NotNull
    @Positive
    @Column(name = "amount_paid", precision = 10, scale = 2)
    private BigDecimal amountPaid;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    
    @Column(name = "payment_method")
    private String paymentMethod; // CASH, CARD, UPI, BANK_TRANSFER, CHEQUE
    
    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "semester")
    private Integer semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    @Column(name = "late_fee", precision = 10, scale = 2)
    private BigDecimal lateFee;
    
    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;
    
    @Column(name = "processed_by")
    private String processedBy;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    // Default constructor
    public FeePayment() {}
    
    // Constructor
    public FeePayment(Student student, FeeStructure feeStructure, BigDecimal amountPaid, PaymentStatus paymentStatus) {
        this.student = student;
        this.feeStructure = feeStructure;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
    }
    
    // Getters and Setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public FeeStructure getFeeStructure() {
        return feeStructure;
    }
    
    public void setFeeStructure(FeeStructure feeStructure) {
        this.feeStructure = feeStructure;
    }
    
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
    
    public BigDecimal getLateFee() {
        return lateFee;
    }
    
    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }
    
    public BigDecimal getDiscount() {
        return discount;
    }
    
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
    public String getProcessedBy() {
        return processedBy;
    }
    
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
    
    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
    
    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
