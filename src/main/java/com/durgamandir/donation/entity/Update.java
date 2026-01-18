package com.durgamandir.donation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "updates")
public class Update {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String title;
    
    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(nullable = true)
    private String imageUrl; // Backward compatibility - single image
    
    @ElementCollection
    @CollectionTable(name = "update_images", joinColumns = @JoinColumn(name = "update_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>(); // Multiple images
    
    @Column(nullable = false)
    private Boolean isFeatured = false;
    
    @Column(nullable = false)
    private Boolean isPublished = true;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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
        // Sync with imageUrls for backward compatibility
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            if (imageUrls == null) {
                imageUrls = new ArrayList<>();
            }
            if (!imageUrls.contains(imageUrl)) {
                imageUrls.add(0, imageUrl); // Add at beginning
            }
        }
    }
    
    public List<String> getImageUrls() {
        // If imageUrls is empty but imageUrl exists, return it as single item
        if ((imageUrls == null || imageUrls.isEmpty()) && imageUrl != null && !imageUrl.trim().isEmpty()) {
            return List.of(imageUrl);
        }
        return imageUrls != null ? imageUrls : new ArrayList<>();
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
        // Sync first image to imageUrl for backward compatibility
        if (imageUrls != null && !imageUrls.isEmpty()) {
            this.imageUrl = imageUrls.get(0);
        }
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
        this.isPublished = isPublished != null ? isPublished : true;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}






