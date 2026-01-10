package com.durgamandir.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MorningAartiVisitResponse {
    private Long id;
    private String name;
    private LocalDate visitDate;
    private Integer familyMembers;
    private LocalDateTime createdAt;
    
    public MorningAartiVisitResponse() {}
    
    public MorningAartiVisitResponse(Long id, String name, LocalDate visitDate, 
                                    Integer familyMembers, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.visitDate = visitDate;
        this.familyMembers = familyMembers;
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
    
    public LocalDate getVisitDate() {
        return visitDate;
    }
    
    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }
    
    public Integer getFamilyMembers() {
        return familyMembers;
    }
    
    public void setFamilyMembers(Integer familyMembers) {
        this.familyMembers = familyMembers;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




