package com.durgamandir.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MorningAartiVisitRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Visit date is required")
    private LocalDate visitDate;
    
    private Integer familyMembers;
    
    // Getters and Setters
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
}




