package com.durgamandir.donation.dto;

import java.math.BigDecimal;

public class DonationStatsResponse {
    private BigDecimal totalAmount;
    private Long totalDonors;
    private Long last7DaysCount;
    private BigDecimal targetAmount;
    private BigDecimal totalExpenses;
    private BigDecimal remainingAmount;
    
    public DonationStatsResponse() {}
    
    public DonationStatsResponse(BigDecimal totalAmount, Long totalDonors, 
                                Long last7DaysCount, BigDecimal targetAmount) {
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
        this.totalDonors = totalDonors != null ? totalDonors : 0L;
        this.last7DaysCount = last7DaysCount != null ? last7DaysCount : 0L;
        this.targetAmount = targetAmount;
        this.totalExpenses = BigDecimal.ZERO;
        this.remainingAmount = this.totalAmount;
    }
    
    public DonationStatsResponse(BigDecimal totalAmount, Long totalDonors, 
                                Long last7DaysCount, BigDecimal targetAmount,
                                BigDecimal totalExpenses, BigDecimal remainingAmount) {
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
        this.totalDonors = totalDonors != null ? totalDonors : 0L;
        this.last7DaysCount = last7DaysCount != null ? last7DaysCount : 0L;
        this.targetAmount = targetAmount;
        this.totalExpenses = totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
        this.remainingAmount = remainingAmount != null ? remainingAmount : BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getTotalDonors() {
        return totalDonors;
    }
    
    public void setTotalDonors(Long totalDonors) {
        this.totalDonors = totalDonors;
    }
    
    public Long getLast7DaysCount() {
        return last7DaysCount;
    }
    
    public void setLast7DaysCount(Long last7DaysCount) {
        this.last7DaysCount = last7DaysCount;
    }
    
    public BigDecimal getTargetAmount() {
        return targetAmount;
    }
    
    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }
    
    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }
    
    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
    
    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }
    
    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}



