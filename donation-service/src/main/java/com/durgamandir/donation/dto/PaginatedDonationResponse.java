package com.durgamandir.donation.dto;

import java.util.List;

public class PaginatedDonationResponse {
    private List<DonationResponse> donations;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    
    public PaginatedDonationResponse() {}
    
    public PaginatedDonationResponse(List<DonationResponse> donations, int currentPage, int totalPages, long totalElements, int pageSize) {
        this.donations = donations;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
    }
    
    // Getters and Setters
    public List<DonationResponse> getDonations() {
        return donations;
    }
    
    public void setDonations(List<DonationResponse> donations) {
        this.donations = donations;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

