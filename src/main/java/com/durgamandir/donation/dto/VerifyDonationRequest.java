package com.durgamandir.donation.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyDonationRequest {
    
    @NotBlank(message = "Admin note is required")
    private String adminNote;
    
    public VerifyDonationRequest() {}
    
    public VerifyDonationRequest(String adminNote) {
        this.adminNote = adminNote;
    }
    
    public String getAdminNote() {
        return adminNote;
    }
    
    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}

