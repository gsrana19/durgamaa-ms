package com.durgamandir.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SpecialPujaResponse {
    private Long id;
    private String pujaType;
    private String devoteeName;
    private String gotra;
    private String city;
    private LocalDate preferredDate;
    private String timeSlot;
    private String intention;
    private String status;
    private LocalDateTime createdAt;
    
    public SpecialPujaResponse() {}
    
    public SpecialPujaResponse(Long id, String pujaType, String devoteeName, String gotra, 
                             String city, LocalDate preferredDate, String timeSlot, 
                             String intention, String status, LocalDateTime createdAt) {
        this.id = id;
        this.pujaType = pujaType;
        this.devoteeName = devoteeName;
        this.gotra = gotra;
        this.city = city;
        this.preferredDate = preferredDate;
        this.timeSlot = timeSlot;
        this.intention = intention;
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




