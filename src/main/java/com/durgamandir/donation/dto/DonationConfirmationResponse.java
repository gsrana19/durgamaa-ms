package com.durgamandir.donation.dto;

import com.durgamandir.donation.entity.DonationConfirmation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DonationConfirmationResponse {
    private Long id;
    private String name;
    private String mobile;
    private BigDecimal amount;
    private String method;
    private String utr;
    private String transactionScreenshot; // URL to uploaded transaction screenshot
    private String message;
    private String purpose; // e.g., "Donation", "Seva Booking - Annapurna Seva"
    private DonationConfirmation.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private String verifiedBy;
    private String adminNote;
    
    // Constructor
    public DonationConfirmationResponse() {}
    
    public DonationConfirmationResponse(DonationConfirmation confirmation) {
        this.id = confirmation.getId();
        this.name = confirmation.getName();
        this.mobile = confirmation.getMobile();
        this.amount = confirmation.getAmount();
        this.method = confirmation.getMethod();
        this.utr = confirmation.getUtr();
        this.transactionScreenshot = confirmation.getTransactionScreenshot();
        this.message = confirmation.getMessage();
        this.purpose = confirmation.getPurpose();
        this.status = confirmation.getStatus();
        this.createdAt = confirmation.getCreatedAt();
        this.verifiedAt = confirmation.getVerifiedAt();
        this.verifiedBy = confirmation.getVerifiedBy();
        this.adminNote = confirmation.getAdminNote();
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
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public DonationConfirmation.Status getStatus() {
        return status;
    }
    
    public void setStatus(DonationConfirmation.Status status) {
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
    
    public String getTransactionScreenshot() {
        return transactionScreenshot;
    }
    
    public void setTransactionScreenshot(String transactionScreenshot) {
        this.transactionScreenshot = transactionScreenshot;
    }
}

