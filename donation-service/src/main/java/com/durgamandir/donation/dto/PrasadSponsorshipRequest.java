package com.durgamandir.donation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PrasadSponsorshipRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    private String occasion;
    
    @NotNull(message = "Preferred date is required")
    private LocalDate preferredDate;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOccasion() {
        return occasion;
    }
    
    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
    
    public LocalDate getPreferredDate() {
        return preferredDate;
    }
    
    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }
}




