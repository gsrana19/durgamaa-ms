package com.durgamandir.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class SevaBookingRequest {
    @NotBlank(message = "Seva name is required")
    private String sevaName;
    
    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;
    
    @NotBlank(message = "Devotee name is required")
    private String devoteeName;
    
    private String gotra;
    
    @NotBlank(message = "Phone or email is required")
    private String phoneOrEmail;
    
    private String specialIntentions;
    
    // Getters and Setters
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
}




