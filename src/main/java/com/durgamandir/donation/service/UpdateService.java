package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.UpdateRequest;
import com.durgamandir.donation.dto.UpdateResponse;
import com.durgamandir.donation.entity.Update;
import com.durgamandir.donation.repository.UpdateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UpdateService {
    
    private final UpdateRepository updateRepository;
    
    public UpdateService(UpdateRepository updateRepository) {
        this.updateRepository = updateRepository;
    }
    
    public List<UpdateResponse> getAllUpdates() {
        return updateRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public UpdateResponse getLatestUpdateWithImage() {
        // First try to find a featured update with image
        return updateRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .filter(update -> {
                    // Check if update has images (either imageUrl or imageUrls)
                    return (update.getImageUrl() != null && !update.getImageUrl().trim().isEmpty()) ||
                           (update.getImageUrls() != null && !update.getImageUrls().isEmpty());
                })
                .filter(update -> Boolean.TRUE.equals(update.getIsFeatured()))
                .findFirst()
                .map(this::mapToResponse)
                .orElseGet(() -> {
                    // Fallback to latest update with image if no featured image
                    return updateRepository.findAllByOrderByCreatedAtDesc()
                            .stream()
                            .filter(update -> {
                                // Check if update has images (either imageUrl or imageUrls)
                                return (update.getImageUrl() != null && !update.getImageUrl().trim().isEmpty()) ||
                                       (update.getImageUrls() != null && !update.getImageUrls().isEmpty());
                            })
                            .findFirst()
                            .map(this::mapToResponse)
                            .orElse(null);
                });
    }
    
    public UpdateResponse createUpdate(UpdateRequest request) {
        Update update = new Update();
        update.setTitle(request.getTitle());
        update.setMessage(request.getMessage());
        update.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        update.setIsPublished(request.getIsPublished() != null ? request.getIsPublished() : true);
        
        // Handle images - prefer imageUrls over imageUrl
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            update.setImageUrls(request.getImageUrls());
        } else if (request.getImageUrl() != null && !request.getImageUrl().trim().isEmpty()) {
            update.setImageUrl(request.getImageUrl());
        }
        
        Update saved = updateRepository.save(update);
        return mapToResponse(saved);
    }
    
    public UpdateResponse updateUpdate(Long id, UpdateRequest request) {
        Update update = updateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Update not found"));
        
        update.setTitle(request.getTitle());
        update.setMessage(request.getMessage());
        
        // Handle images - prefer imageUrls over imageUrl
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            update.setImageUrls(request.getImageUrls());
        } else if (request.getImageUrl() != null && !request.getImageUrl().trim().isEmpty()) {
            update.setImageUrl(request.getImageUrl());
        }
        
        if (request.getIsFeatured() != null) {
            update.setIsFeatured(request.getIsFeatured());
        }
        if (request.getIsPublished() != null) {
            update.setIsPublished(request.getIsPublished());
        }
        
        Update updated = updateRepository.save(update);
        return mapToResponse(updated);
    }
    
    public UpdateResponse setFeaturedImage(Long id, Boolean featured) {
        Update update = updateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Update not found"));
        
        if (featured && update.getImageUrl() == null) {
            throw new IllegalArgumentException("Cannot set featured status for update without image");
        }
        
        // If setting as featured, unset all other featured updates
        if (featured) {
            updateRepository.findAll().forEach(u -> {
                if (Boolean.TRUE.equals(u.getIsFeatured()) && !u.getId().equals(id)) {
                    u.setIsFeatured(false);
                    updateRepository.save(u);
                }
            });
        }
        
        update.setIsFeatured(featured);
        Update saved = updateRepository.save(update);
        return mapToResponse(saved);
    }
    
    public void deleteUpdate(Long id) {
        updateRepository.deleteById(id);
    }
    
    public List<UpdateResponse> getPublicUpdates(int limit) {
        List<Update> updates = updateRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .filter(update -> Boolean.TRUE.equals(update.getIsPublished()))
                .limit(limit > 0 ? limit : Integer.MAX_VALUE)
                .collect(java.util.stream.Collectors.toList());
        return updates.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<UpdateResponse> getAllImages() {
        // Flatten all images from published updates
        return updateRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .filter(update -> Boolean.TRUE.equals(update.getIsPublished()))
                .filter(update -> {
                    List<String> images = update.getImageUrls();
                    return images != null && !images.isEmpty();
                })
                .flatMap(update -> {
                    List<String> images = update.getImageUrls();
                    return images.stream().map(imageUrl -> {
                        UpdateResponse response = mapToResponse(update);
                        response.setImageUrls(List.of(imageUrl)); // Single image per response
                        response.setMessage(null); // Don't include message in gallery
                        return response;
                    });
                })
                .collect(Collectors.toList());
    }
    
    private UpdateResponse mapToResponse(Update update) {
        return new UpdateResponse(
                update.getId(),
                update.getTitle(),
                update.getMessage(),
                update.getImageUrl(),
                update.getImageUrls(),
                update.getIsFeatured(),
                update.getIsPublished(),
                update.getCreatedAt()
        );
    }
}





