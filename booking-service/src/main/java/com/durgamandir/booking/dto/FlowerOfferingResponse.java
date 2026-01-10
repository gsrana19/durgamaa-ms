package com.durgamandir.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlowerOfferingResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private String flowerType;
    private LocalDateTime createdAt;
    
    public FlowerOfferingResponse() {}
    
    public FlowerOfferingResponse(Long id, String name, LocalDate date, 
                                 String flowerType, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.flowerType = flowerType;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




