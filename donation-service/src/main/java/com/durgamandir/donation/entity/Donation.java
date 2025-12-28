package com.durgamandir.donation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = true)
    private String email;
    
    @NotNull
    @Column(nullable = false)
    private String phone;
    
    @NotNull
    @Positive
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private Long countryId;
    
    @Column(nullable = false)
    private Long stateId;
    
    @Column(nullable = false)
    private Long districtId;
    
    @Column(nullable = false)
    private Long thanaId;
    
    @Column(nullable = true)
    private Long villageId;
    
    @Column(nullable = true)
    private String customVillageName;
    
    // Keep city field for backward compatibility (can be derived from district)
    @Column(nullable = true)
    private String city;
    
    @Column(nullable = false)
    private Boolean showPublic = true;
    
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
    
    public String getCity() {
        return city;
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
    
    public void setCity(String city) {
        this.city = city;
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
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

