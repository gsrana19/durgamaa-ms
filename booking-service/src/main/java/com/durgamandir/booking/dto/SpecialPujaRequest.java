package com.durgamandir.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class SpecialPujaRequest {
    @NotBlank(message = "Puja type is required")
    private String pujaType;
    
    @NotBlank(message = "Devotee name is required")
    private String devoteeName;
    
    private String gotra;
    
    private String city;
    
    @NotNull(message = "Preferred date is required")
    private LocalDate preferredDate;
    
    @NotBlank(message = "Time slot is required")
    private String timeSlot;
    
    @NotBlank(message = "Intention is required")
    private String intention;
    
    // Getters and Setters
    public String getPujaType() {
        return pujaType;
    }
    
    public void setPujaType(String pujaType) {
        this.pujaType = pujaType;
    }
    
    public String getDevoteeName() {
        return devoteeName;
    }
    
    public void setDevoteeName(String devoteeName) {
        this.devoteeName = devoteeName;
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
    
    public LocalDate getPreferredDate() {
        return preferredDate;
    }
    
    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }
    
    public String getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public String getIntention() {
        return intention;
    }
    
    public void setIntention(String intention) {
        this.intention = intention;
    }
}




