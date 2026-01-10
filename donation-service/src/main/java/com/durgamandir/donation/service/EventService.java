package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.EventRequest;
import com.durgamandir.donation.dto.EventResponse;
import com.durgamandir.donation.entity.Event;
import com.durgamandir.donation.entity.EventMedia;
import com.durgamandir.donation.repository.EventMediaRepository;
import com.durgamandir.donation.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    
    private final EventRepository eventRepository;
    private final EventMediaRepository eventMediaRepository;
    
    public EventService(EventRepository eventRepository, EventMediaRepository eventMediaRepository) {
        this.eventRepository = eventRepository;
        this.eventMediaRepository = eventMediaRepository;
    }
    
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public EventResponse createEvent(EventRequest request) {
        // Validate that at least one language is provided for each field
        if ((request.getName() == null || request.getName().trim().isEmpty()) && 
            (request.getNameHi() == null || request.getNameHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Event name is required in at least one language (English or Hindi)");
        }
        if ((request.getDateRange() == null || request.getDateRange().trim().isEmpty()) && 
            (request.getDateRangeHi() == null || request.getDateRangeHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Date range is required in at least one language (English or Hindi)");
        }
        if ((request.getShortDescription() == null || request.getShortDescription().trim().isEmpty()) && 
            (request.getShortDescriptionHi() == null || request.getShortDescriptionHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Short description is required in at least one language (English or Hindi)");
        }
        if ((request.getMorningSchedule() == null || request.getMorningSchedule().trim().isEmpty()) && 
            (request.getMorningScheduleHi() == null || request.getMorningScheduleHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Morning schedule is required in at least one language (English or Hindi)");
        }
        if ((request.getAfternoonSchedule() == null || request.getAfternoonSchedule().trim().isEmpty()) && 
            (request.getAfternoonScheduleHi() == null || request.getAfternoonScheduleHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Afternoon schedule is required in at least one language (English or Hindi)");
        }
        if ((request.getEveningSchedule() == null || request.getEveningSchedule().trim().isEmpty()) && 
            (request.getEveningScheduleHi() == null || request.getEveningScheduleHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Evening schedule is required in at least one language (English or Hindi)");
        }
        
        Event event = new Event();
        event.setName(request.getName() != null ? request.getName() : "");
        event.setNameHi(request.getNameHi() != null ? request.getNameHi() : "");
        event.setDateRange(request.getDateRange() != null ? request.getDateRange() : "");
        event.setDateRangeHi(request.getDateRangeHi() != null ? request.getDateRangeHi() : "");
        event.setShortDescription(request.getShortDescription() != null ? request.getShortDescription() : "");
        event.setShortDescriptionHi(request.getShortDescriptionHi() != null ? request.getShortDescriptionHi() : "");
        event.setMorningSchedule(request.getMorningSchedule() != null ? request.getMorningSchedule() : "");
        event.setMorningScheduleHi(request.getMorningScheduleHi() != null ? request.getMorningScheduleHi() : "");
        event.setAfternoonSchedule(request.getAfternoonSchedule() != null ? request.getAfternoonSchedule() : "");
        event.setAfternoonScheduleHi(request.getAfternoonScheduleHi() != null ? request.getAfternoonScheduleHi() : "");
        event.setEveningSchedule(request.getEveningSchedule() != null ? request.getEveningSchedule() : "");
        event.setEveningScheduleHi(request.getEveningScheduleHi() != null ? request.getEveningScheduleHi() : "");
        
        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }
    
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        
        // Validate that at least one language is provided for each field
        if ((request.getName() == null || request.getName().trim().isEmpty()) && 
            (request.getNameHi() == null || request.getNameHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Event name is required in at least one language (English or Hindi)");
        }
        if ((request.getDateRange() == null || request.getDateRange().trim().isEmpty()) && 
            (request.getDateRangeHi() == null || request.getDateRangeHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Date range is required in at least one language (English or Hindi)");
        }
        if ((request.getShortDescription() == null || request.getShortDescription().trim().isEmpty()) && 
            (request.getShortDescriptionHi() == null || request.getShortDescriptionHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Short description is required in at least one language (English or Hindi)");
        }
        if ((request.getMorningSchedule() == null || request.getMorningSchedule().trim().isEmpty()) && 
            (request.getMorningScheduleHi() == null || request.getMorningScheduleHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Morning schedule is required in at least one language (English or Hindi)");
        }
        if ((request.getAfternoonSchedule() == null || request.getAfternoonSchedule().trim().isEmpty()) && 
            (request.getAfternoonScheduleHi() == null || request.getAfternoonScheduleHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Afternoon schedule is required in at least one language (English or Hindi)");
        }
        if ((request.getEveningSchedule() == null || request.getEveningSchedule().trim().isEmpty()) && 
            (request.getEveningScheduleHi() == null || request.getEveningScheduleHi().trim().isEmpty())) {
            throw new IllegalArgumentException("Evening schedule is required in at least one language (English or Hindi)");
        }
        
        event.setName(request.getName() != null ? request.getName() : "");
        event.setNameHi(request.getNameHi() != null ? request.getNameHi() : "");
        event.setDateRange(request.getDateRange() != null ? request.getDateRange() : "");
        event.setDateRangeHi(request.getDateRangeHi() != null ? request.getDateRangeHi() : "");
        event.setShortDescription(request.getShortDescription() != null ? request.getShortDescription() : "");
        event.setShortDescriptionHi(request.getShortDescriptionHi() != null ? request.getShortDescriptionHi() : "");
        event.setMorningSchedule(request.getMorningSchedule() != null ? request.getMorningSchedule() : "");
        event.setMorningScheduleHi(request.getMorningScheduleHi() != null ? request.getMorningScheduleHi() : "");
        event.setAfternoonSchedule(request.getAfternoonSchedule() != null ? request.getAfternoonSchedule() : "");
        event.setAfternoonScheduleHi(request.getAfternoonScheduleHi() != null ? request.getAfternoonScheduleHi() : "");
        event.setEveningSchedule(request.getEveningSchedule() != null ? request.getEveningSchedule() : "");
        event.setEveningScheduleHi(request.getEveningScheduleHi() != null ? request.getEveningScheduleHi() : "");
        
        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }
    
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        
        // Delete associated media files and records
        List<EventMedia> mediaList = eventMediaRepository.findByEventId(id);
        for (EventMedia media : mediaList) {
            // Delete file from filesystem
            try {
                String mediaUrl = media.getMediaUrl();
                if (mediaUrl != null && mediaUrl.startsWith("/uploads/events/")) {
                    String filename = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
                    Path filePath = Paths.get("uploads/events", filename);
                    Files.deleteIfExists(filePath);
                }
            } catch (IOException e) {
                System.err.println("Failed to delete media file: " + e.getMessage());
                // Continue with database deletion even if file deletion fails
            }
        }
        
        // Delete media records from database
        eventMediaRepository.deleteAll(mediaList);
        
        // Delete event
        eventRepository.delete(event);
    }
    
    private EventResponse mapToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getNameHi() != null ? event.getNameHi() : "",
                event.getDateRange(),
                event.getDateRangeHi() != null ? event.getDateRangeHi() : "",
                event.getShortDescription(),
                event.getShortDescriptionHi() != null ? event.getShortDescriptionHi() : "",
                event.getMorningSchedule(),
                event.getMorningScheduleHi() != null ? event.getMorningScheduleHi() : "",
                event.getAfternoonSchedule(),
                event.getAfternoonScheduleHi() != null ? event.getAfternoonScheduleHi() : "",
                event.getEveningSchedule(),
                event.getEveningScheduleHi() != null ? event.getEveningScheduleHi() : "",
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}

