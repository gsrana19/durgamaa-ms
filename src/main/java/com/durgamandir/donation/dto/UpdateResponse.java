package com.durgamandir.donation.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateResponse {
    private Long id;
    private String title;
    private String message;
    private String imageUrl; // Backward compatibility
    private List<String> imageUrls; // Multiple images
    private Boolean isFeatured;
    private Boolean isPublished;
    private LocalDateTime createdAt;
    
    public UpdateResponse() {}
    
    public UpdateResponse(Long id, String title, String message, 
                         String imageUrl, List<String> imageUrls, Boolean isFeatured, Boolean isPublished, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.imageUrls = imageUrls;
        this.isFeatured = isFeatured;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public List<String> getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
    public Boolean getIsFeatured() {
        return isFeatured;
    }
    
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
    
    public Boolean getIsPublished() {
        return isPublished;
    }
    
    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}






