package com.durgamandir.donation.dto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class EventResponse {
    private Long id;
    private String name;
    private String nameHi;
    private String dateRange;
    private String dateRangeHi;
    private String shortDescription;
    private String shortDescriptionHi;
    private Map<String, String> schedule;
    private Map<String, String> scheduleHi;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public EventResponse() {
        this.schedule = new HashMap<>();
        this.scheduleHi = new HashMap<>();
    }
    
    public EventResponse(Long id, String name, String nameHi, String dateRange, String dateRangeHi,
                        String shortDescription, String shortDescriptionHi,
                        String morningSchedule, String morningScheduleHi,
                        String afternoonSchedule, String afternoonScheduleHi,
                        String eveningSchedule, String eveningScheduleHi,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.nameHi = nameHi;
        this.dateRange = dateRange;
        this.dateRangeHi = dateRangeHi;
        this.shortDescription = shortDescription;
        this.shortDescriptionHi = shortDescriptionHi;
        this.schedule = new HashMap<>();
        this.schedule.put("morning", morningSchedule);
        this.schedule.put("afternoon", afternoonSchedule);
        this.schedule.put("evening", eveningSchedule);
        this.scheduleHi = new HashMap<>();
        this.scheduleHi.put("morning", morningScheduleHi);
        this.scheduleHi.put("afternoon", afternoonScheduleHi);
        this.scheduleHi.put("evening", eveningScheduleHi);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    
    public Map<String, String> getSchedule() {
        return schedule;
    }
    
    public void setSchedule(Map<String, String> schedule) {
        this.schedule = schedule;
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
    
    public Map<String, String> getScheduleHi() {
        return scheduleHi;
    }
    
    public void setScheduleHi(Map<String, String> scheduleHi) {
        this.scheduleHi = scheduleHi;
    }
}

