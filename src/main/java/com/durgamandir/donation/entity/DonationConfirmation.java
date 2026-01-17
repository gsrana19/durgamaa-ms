package com.durgamandir.donation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donation_confirmations")
public class DonationConfirmation {
    
    public enum Status {
        PENDING,
        VERIFIED,
        REJECTED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private String name;
    
    @Column(nullable = true)
    private String mobile;
    
    @NotNull
    @Positive
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @NotNull
    @Column(nullable = false)
    private String method; // UPI, Bank Transfer
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String utr; // UTR or Transaction ID
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = true)
    private LocalDateTime verifiedAt;
    
    @Column(nullable = true)
    private String verifiedBy;
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String adminNote;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getUtr() {
        return utr;
    }
    
    public void setUtr(String utr) {
        this.utr = utr;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }
    
    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
    
    public String getVerifiedBy() {
        return verifiedBy;
    }
    
    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
    
    public String getAdminNote() {
        return adminNote;
    }
    
    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}

