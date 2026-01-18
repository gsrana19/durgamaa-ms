package com.durgamandir.donation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class DonationConfirmationRequest {
    
    @NotNull
    @Positive
    private BigDecimal amount;
    
    @NotNull
    private String method; // UPI, Bank Transfer
    
    private String utr; // UTR or Transaction ID (optional if transactionScreenshot is provided)
    
    private String transactionScreenshot; // URL to uploaded transaction screenshot (optional if utr is provided)
    
    private String name;
    
    @NotNull(message = "Mobile number is required")
    private String mobile;
    
    private String message;
    
    private String purpose; // e.g., "Donation", "Seva Booking - Annapurna Seva"
    
    // Getters and Setters
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
    
    public String getTransactionScreenshot() {
        return transactionScreenshot;
    }
    
    public void setTransactionScreenshot(String transactionScreenshot) {
        this.transactionScreenshot = transactionScreenshot;
    }
}

