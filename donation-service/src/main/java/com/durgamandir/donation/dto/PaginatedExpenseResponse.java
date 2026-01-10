package com.durgamandir.donation.dto;

import java.util.List;

public class PaginatedExpenseResponse {
    private List<ExpenseResponse> expenses;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    
    public PaginatedExpenseResponse() {}
    
    public PaginatedExpenseResponse(List<ExpenseResponse> expenses, int currentPage, int totalPages, long totalElements, int pageSize) {
        this.expenses = expenses;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
    }
    
    // Getters and Setters
    public List<ExpenseResponse> getExpenses() {
        return expenses;
    }
    
    public void setExpenses(List<ExpenseResponse> expenses) {
        this.expenses = expenses;
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

