package com.durgamandir.donation.dto;

import jakarta.validation.constraints.NotBlank;

public class SankalpamRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    private String gotra;
    
    private String city;
    
    @NotBlank(message = "Prayer/Intention is required")
    private String prayer;
    
    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getGotra() {
        return gotra;
    }
    
    public void setGotra(String gotra) {
        this.gotra = gotra;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getPrayer() {
        return prayer;
    }
    
    public void setPrayer(String prayer) {
        this.prayer = prayer;
    }
}




