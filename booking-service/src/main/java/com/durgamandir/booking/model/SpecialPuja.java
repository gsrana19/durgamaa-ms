package com.durgamandir.booking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "special_pujas")
public class SpecialPuja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String pujaType; // e.g., "sankalp", "navgrah", "health", "studies"
    
    @Column(nullable = false)
    private String devoteeName;
    
    private String gotra;
    
    private String city;
    
    @Column(nullable = false)
    private LocalDate preferredDate;
    
    @Column(nullable = false)
    private String timeSlot; // "morning" or "evening"
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String intention;
    
    @Column(length = 50)
    private String status; // e.g., "PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
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
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}




