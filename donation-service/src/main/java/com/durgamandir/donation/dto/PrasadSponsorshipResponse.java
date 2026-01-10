package com.durgamandir.donation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrasadSponsorshipResponse {
    private Long id;
    private String name;
    private String occasion;
    private LocalDate preferredDate;
    private LocalDateTime createdAt;
    
    public PrasadSponsorshipResponse() {}
    
    public PrasadSponsorshipResponse(Long id, String name, String occasion, LocalDate preferredDate, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.occasion = occasion;
        this.preferredDate = preferredDate;
        this.createdAt = createdAt;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




