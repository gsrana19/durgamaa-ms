package com.durgamandir.donation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String nameHi; // Hindi name
    
    @Column(nullable = false)
    private String dateRange;
    
    @Column(nullable = false)
    private String dateRangeHi; // Hindi date range
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String shortDescription;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String shortDescriptionHi; // Hindi description
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String morningSchedule;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String morningScheduleHi; // Hindi morning schedule
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String afternoonSchedule;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String afternoonScheduleHi; // Hindi afternoon schedule
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String eveningSchedule;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String eveningScheduleHi; // Hindi evening schedule
    
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDateRange() {
        return dateRange;
    }
    
    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }
    
    public String getShortDescription() {
        return shortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    public String getMorningSchedule() {
        return morningSchedule;
    }
    
    public void setMorningSchedule(String morningSchedule) {
        this.morningSchedule = morningSchedule;
    }
    
    public String getAfternoonSchedule() {
        return afternoonSchedule;
    }
    
    public void setAfternoonSchedule(String afternoonSchedule) {
        this.afternoonSchedule = afternoonSchedule;
    }
    
    public String getEveningSchedule() {
        return eveningSchedule;
    }
    
    public void setEveningSchedule(String eveningSchedule) {
        this.eveningSchedule = eveningSchedule;
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
    
    public String getNameHi() {
        return nameHi;
    }
    
    public void setNameHi(String nameHi) {
        this.nameHi = nameHi;
    }
    
    public String getDateRangeHi() {
        return dateRangeHi;
    }
    
    public void setDateRangeHi(String dateRangeHi) {
        this.dateRangeHi = dateRangeHi;
    }
    
    public String getShortDescriptionHi() {
        return shortDescriptionHi;
    }
    
    public void setShortDescriptionHi(String shortDescriptionHi) {
        this.shortDescriptionHi = shortDescriptionHi;
    }
    
    public String getMorningScheduleHi() {
        return morningScheduleHi;
    }
    
    public void setMorningScheduleHi(String morningScheduleHi) {
        this.morningScheduleHi = morningScheduleHi;
    }
    
    public String getAfternoonScheduleHi() {
        return afternoonScheduleHi;
    }
    
    public void setAfternoonScheduleHi(String afternoonScheduleHi) {
        this.afternoonScheduleHi = afternoonScheduleHi;
    }
    
    public String getEveningScheduleHi() {
        return eveningScheduleHi;
    }
    
    public void setEveningScheduleHi(String eveningScheduleHi) {
        this.eveningScheduleHi = eveningScheduleHi;
    }
}

