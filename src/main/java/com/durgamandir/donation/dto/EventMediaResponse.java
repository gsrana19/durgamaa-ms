package com.durgamandir.donation.dto;

import java.time.LocalDateTime;

public class EventMediaResponse {
    private Long id;
    private Long eventId;
    private String mediaUrl;
    private String mediaType;
    private String originalName;
    private LocalDateTime createdAt;
    private Boolean deleted;
    private LocalDateTime deletedAt;
    
    public EventMediaResponse() {}
    
    public EventMediaResponse(Long id, Long eventId, String mediaUrl, String mediaType, 
                             String originalName, LocalDateTime createdAt, Boolean deleted, LocalDateTime deletedAt) {
        this.id = id;
        this.eventId = eventId;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.originalName = originalName;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getEventId() {
        return eventId;
    }
    
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    
    public String getMediaUrl() {
        return mediaUrl;
    }
    
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
    
    public String getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getOriginalName() {
        return originalName;
    }
    
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Boolean getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

