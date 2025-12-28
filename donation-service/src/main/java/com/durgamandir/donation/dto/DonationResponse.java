package com.durgamandir.donation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DonationResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private BigDecimal amount;
    private Long countryId;
    private String countryName;
    private Long stateId;
    private String stateName;
    private Long districtId;
    private String districtName;
    private Long thanaId;
    private String thanaName;
    private Long villageId;
    private String villageName;
    private String customVillageName;
    private Boolean showPublic;
    private LocalDateTime createdAt;
    
    public DonationResponse() {}
    
    public DonationResponse(Long id, String name, String email, String phone, 
                           BigDecimal amount, Long countryId, String countryName,
                           Long stateId, String stateName, Long districtId, String districtName,
                           Long thanaId, String thanaName, Long villageId, String villageName,
                           String customVillageName, Boolean showPublic, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.amount = amount;
        this.countryId = countryId;
        this.countryName = countryName;
        this.stateId = stateId;
        this.stateName = stateName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.thanaId = thanaId;
        this.thanaName = thanaName;
        this.villageId = villageId;
        this.villageName = villageName;
        this.customVillageName = customVillageName;
        this.showPublic = showPublic;
        this.createdAt = createdAt;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Long getCountryId() {
        return countryId;
    }
    
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public Long getStateId() {
        return stateId;
    }
    
    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
    
    public String getStateName() {
        return stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    
    public Long getDistrictId() {
        return districtId;
    }
    
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
    
    public String getDistrictName() {
        return districtName;
    }
    
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
    public Long getThanaId() {
        return thanaId;
    }
    
    public void setThanaId(Long thanaId) {
        this.thanaId = thanaId;
    }
    
    public String getThanaName() {
        return thanaName;
    }
    
    public void setThanaName(String thanaName) {
        this.thanaName = thanaName;
    }
    
    public Long getVillageId() {
        return villageId;
    }
    
    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }
    
    public String getVillageName() {
        return villageName;
    }
    
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
    
    public String getCustomVillageName() {
        return customVillageName;
    }
    
    public void setCustomVillageName(String customVillageName) {
        this.customVillageName = customVillageName;
    }
    
    public Boolean getShowPublic() {
        return showPublic;
    }
    
    public void setShowPublic(Boolean showPublic) {
        this.showPublic = showPublic;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

