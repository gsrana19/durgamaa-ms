package com.durgamandir.donation.dto;

import java.time.LocalDateTime;

public class SankalpamResponse {
    private Long id;
    private String fullName;
    private String gotra;
    private String city;
    private String prayer;
    private LocalDateTime createdAt;
    
    public SankalpamResponse() {}
    
    public SankalpamResponse(Long id, String fullName, String gotra, String city, String prayer, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.gotra = gotra;
        this.city = city;
        this.prayer = prayer;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




