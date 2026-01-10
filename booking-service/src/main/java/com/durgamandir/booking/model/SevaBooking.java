package com.durgamandir.booking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seva_bookings")
public class SevaBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String sevaName;
    
    @Column(nullable = false)
    private LocalDate bookingDate;
    
    @Column(nullable = false)
    private String devoteeName;
    
    private String gotra;
    
    @Column(nullable = false)
    private String phoneOrEmail;
    
    @Column(columnDefinition = "TEXT")
    private String specialIntentions;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}




