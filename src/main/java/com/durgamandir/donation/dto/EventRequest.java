package com.durgamandir.donation.dto;

import jakarta.validation.constraints.NotBlank;

public class EventRequest {
    // English fields (optional - at least one language required)
    private String name;
    private String dateRange;
    private String shortDescription;
    private String morningSchedule;
    private String afternoonSchedule;
    private String eveningSchedule;
    
    // Hindi fields (optional)
    private String nameHi;
    private String dateRangeHi;
    private String shortDescriptionHi;
    private String morningScheduleHi;
    private String afternoonScheduleHi;
    private String eveningScheduleHi;
    
    // Getters and Setters
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

