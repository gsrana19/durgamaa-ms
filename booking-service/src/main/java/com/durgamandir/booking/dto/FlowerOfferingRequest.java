package com.durgamandir.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class FlowerOfferingRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    private String flowerType;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getFlowerType() {
        return flowerType;
    }
    
    public void setFlowerType(String flowerType) {
        this.flowerType = flowerType;
    }
}




