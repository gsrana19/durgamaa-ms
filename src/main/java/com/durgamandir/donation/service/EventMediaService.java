package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.EventMediaResponse;
import com.durgamandir.donation.entity.Event;
import com.durgamandir.donation.entity.EventMedia;
import com.durgamandir.donation.repository.EventMediaRepository;
import com.durgamandir.donation.repository.EventRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventMediaService {
    
    private final EventMediaRepository eventMediaRepository;
    private final EventRepository eventRepository;
    
    @Value("${app.media.upload-dir:./uploads/events}")
    private String uploadDir;
    
    @Value("${app.media.soft-delete.enabled:true}")
    private boolean softDeleteEnabled;
    
    @Value("${app.media.soft-delete.auto-restore:true}")
    private boolean autoRestoreEnabled;
    
    public EventMediaService(EventMediaRepository eventMediaRepository, EventRepository eventRepository) {
        this.eventMediaRepository = eventMediaRepository;
        this.eventRepository = eventRepository;
    }
    
    @PostConstruct
    public void init() {
        // Initialize upload directory after @Value injection
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to create upload directory: " + e.getMessage());
        }
    }
    
    public EventMediaResponse uploadMedia(Long eventId, MultipartFile file) throws IOException {
        // Validate event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));
        
        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        // Determine media type
        String contentType = file.getContentType();
        String mediaType;
        if (contentType != null && contentType.startsWith("image/")) {
            mediaType = "IMAGE";
        } else if (contentType != null && (contentType.startsWith("video/") || contentType.equals("application/octet-stream"))) {
            mediaType = "VIDEO";
        } else {
            throw new IllegalArgumentException("File must be an image or video");
        }
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        
        // Save file - ensure upload directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // Create EventMedia entity
        EventMedia eventMedia = new EventMedia();
        eventMedia.setEvent(event);
        eventMedia.setMediaUrl("/uploads/events/" + filename);
        eventMedia.setMediaType(mediaType);
        eventMedia.setOriginalName(originalFilename);
        
        EventMedia saved = eventMediaRepository.save(eventMedia);
        return mapToResponse(saved);
    }
    
    public List<EventMediaResponse> getMediaByEventId(Long eventId) {
        // Get only non-deleted media
        List<EventMedia> mediaList = eventMediaRepository.findByEventIdAndDeletedFalse(eventId);
        
        // Auto-restore if enabled (check for any deleted media that should be restored)
        if (autoRestoreEnabled) {
            List<EventMedia> deletedMedia = eventMediaRepository.findByEventIdAndDeletedTrue(eventId);
            for (EventMedia media : deletedMedia) {
                media.setDeleted(false);
                media.setDeletedAt(null);
                eventMediaRepository.save(media);
            }
            // Reload after auto-restore
            mediaList = eventMediaRepository.findByEventIdAndDeletedFalse(eventId);
        }
        
        return mediaList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<EventMediaResponse> getDeletedMediaByEventId(Long eventId) {
        // Get only deleted media
        List<EventMedia> mediaList = eventMediaRepository.findByEventIdAndDeletedTrue(eventId);
        return mediaList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<EventMediaResponse> getAllMediaByEventId(Long eventId, boolean includeDeleted) {
        if (includeDeleted) {
            return eventMediaRepository.findByEventId(eventId)
                    .stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } else {
            return getMediaByEventId(eventId);
        }
    }
    
    public void deleteMedia(Long mediaId) {
        EventMedia eventMedia = eventMediaRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Media not found with id: " + mediaId));
        
        if (softDeleteEnabled) {
            // Soft delete: mark as deleted but keep file
            eventMedia.setDeleted(true);
            eventMedia.setDeletedAt(LocalDateTime.now());
            eventMediaRepository.save(eventMedia);
        } else {
            // Hard delete: remove file and database record
            try {
                String mediaUrl = eventMedia.getMediaUrl();
                if (mediaUrl != null && mediaUrl.startsWith("/uploads/events/")) {
                    String filename = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
                    Path filePath = Paths.get(uploadDir, filename);
                    Files.deleteIfExists(filePath);
                }
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + e.getMessage());
                // Continue with database deletion even if file deletion fails
            }
            
            // Delete from database
            eventMediaRepository.delete(eventMedia);
        }
    }
    
    public void restoreMedia(Long mediaId) {
        EventMedia eventMedia = eventMediaRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Media not found with id: " + mediaId));
        
        if (!eventMedia.getDeleted()) {
            throw new IllegalArgumentException("Media is not deleted");
        }
        
        // Restore: unmark as deleted
        eventMedia.setDeleted(false);
        eventMedia.setDeletedAt(null);
        eventMediaRepository.save(eventMedia);
    }
    
    public void permanentlyDeleteMedia(Long mediaId) {
        EventMedia eventMedia = eventMediaRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Media not found with id: " + mediaId));
        
        if (!eventMedia.getDeleted()) {
            throw new IllegalArgumentException("Media must be soft-deleted first before permanent deletion");
        }
        
        // Permanently delete file and database record
        try {
            String mediaUrl = eventMedia.getMediaUrl();
            if (mediaUrl != null && mediaUrl.startsWith("/uploads/events/")) {
                String filename = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
                Path filePath = Paths.get(uploadDir, filename);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
            // Continue with database deletion even if file deletion fails
        }
        
        // Delete from database
        eventMediaRepository.delete(eventMedia);
    }
    
    private EventMediaResponse mapToResponse(EventMedia eventMedia) {
        return new EventMediaResponse(
                eventMedia.getId(),
                eventMedia.getEvent().getId(),
                eventMedia.getMediaUrl(),
                eventMedia.getMediaType(),
                eventMedia.getOriginalName(),
                eventMedia.getCreatedAt(),
                eventMedia.getDeleted(),
                eventMedia.getDeletedAt()
        );
    }
}

