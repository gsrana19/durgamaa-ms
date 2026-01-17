package com.durgamandir.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SevaBookingResponse {
    private Long id;
    private String sevaName;
    private LocalDate bookingDate;
    private String devoteeName;
    private String gotra;
    private String phoneOrEmail;
    private String specialIntentions;
    private LocalDateTime createdAt;
    
    public SevaBookingResponse() {}
    
    public SevaBookingResponse(Long id, String sevaName, LocalDate bookingDate, String devoteeName,
                               String gotra, String phoneOrEmail, String specialIntentions, LocalDateTime createdAt) {
        this.id = id;
        this.sevaName = sevaName;
        this.bookingDate = bookingDate;
        this.devoteeName = devoteeName;
        this.gotra = gotra;
        this.phoneOrEmail = phoneOrEmail;
        this.specialIntentions = specialIntentions;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSevaName() {
        return sevaName;
    }
    
    public void setSevaName(String sevaName) {
        this.sevaName = sevaName;
    }
    
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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
    
    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }
    
    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }
    
    public String getSpecialIntentions() {
        return specialIntentions;
    }
    
    public void setSpecialIntentions(String specialIntentions) {
        this.specialIntentions = specialIntentions;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}




