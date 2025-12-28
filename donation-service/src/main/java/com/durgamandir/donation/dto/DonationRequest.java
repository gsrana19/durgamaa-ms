package com.durgamandir.donation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class DonationRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    private String phone;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Country ID is required")
    private Long countryId;
    
    @NotNull(message = "State ID is required")
    private Long stateId;
    
    @NotNull(message = "District ID is required")
    private Long districtId;
    
    @NotNull(message = "Thana ID is required")
    private Long thanaId;
    
    private Long villageId;
    
    private String customVillageName;
    
    @NotNull(message = "Show public status is required")
    private Boolean showPublic;
    
    // Getters and Setters
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
    
    public Long getStateId() {
        return stateId;
    }
    
    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
    
    public Long getDistrictId() {
        return districtId;
    }
    
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
    
    public Long getThanaId() {
        return thanaId;
    }
    
    public void setThanaId(Long thanaId) {
        this.thanaId = thanaId;
    }
    
    public Long getVillageId() {
        return villageId;
    }
    
    public void setVillageId(Long villageId) {
        this.villageId = villageId;
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
}

