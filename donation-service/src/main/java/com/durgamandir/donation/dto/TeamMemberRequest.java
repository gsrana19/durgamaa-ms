package com.durgamandir.donation.dto;

import jakarta.validation.constraints.NotBlank;

public class TeamMemberRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Position is required")
    private String position;
    
    private String mobileNumber;
    
    private Integer displayOrder = 0;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}

