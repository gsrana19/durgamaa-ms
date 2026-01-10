package com.durgamandir.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AbhishekamBookingRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    private String gotra;
    
    @NotNull(message = "Preferred date is required")
    private LocalDate preferredDate;
    
    private Integer familyMembers;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGotra() {
        return gotra;
    }
    
    public void setGotra(String gotra) {
        this.gotra = gotra;
    }
    
    public LocalDate getPreferredDate() {
        return preferredDate;
    }
    
    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }
    
    public Integer getFamilyMembers() {
        return familyMembers;
    }
    
    public void setFamilyMembers(Integer familyMembers) {
        this.familyMembers = familyMembers;
    }
}




