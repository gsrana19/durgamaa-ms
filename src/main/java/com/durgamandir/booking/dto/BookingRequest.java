package com.durgamandir.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingRequest {
    @NotBlank(message = "Booking type is required")
    private String bookingType; // e.g., "SEVA", "PUJA", "PRASAD"
    
    @NotBlank(message = "Service name is required")
    private String serviceName; // e.g., "Annapurna Seva", "Morning Puja"
    
    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;
    
    @NotBlank(message = "Devotee name is required")
    private String devoteeName;
    
    private String gotra;
    
    @NotBlank(message = "Phone or email is required")
    private String phoneOrEmail;
    
    private String specialIntentions;
    
    private String notes;
    
    // Getters and Setters
    public String getBookingType() {
        return bookingType;
    }
    
    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}




