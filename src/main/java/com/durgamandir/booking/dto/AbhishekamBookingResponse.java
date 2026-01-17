package com.durgamandir.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AbhishekamBookingResponse {
    private Long id;
    private String name;
    private String gotra;
    private LocalDate preferredDate;
    private Integer familyMembers;
    private String status;
    private LocalDateTime createdAt;
    
    public AbhishekamBookingResponse() {}
    
    public AbhishekamBookingResponse(Long id, String name, String gotra, LocalDate preferredDate, 
                                    Integer familyMembers, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.gotra = gotra;
        this.preferredDate = preferredDate;
        this.familyMembers = familyMembers;
        this.status = status;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




