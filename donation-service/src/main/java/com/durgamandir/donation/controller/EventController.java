package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.EventMediaResponse;
import com.durgamandir.donation.dto.EventRequest;
import com.durgamandir.donation.dto.EventResponse;
import com.durgamandir.donation.dto.TeamMemberResponse;
import com.durgamandir.donation.service.EventMediaService;
import com.durgamandir.donation.service.EventService;
import com.durgamandir.donation.service.TeamMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    private final EventService eventService;
    private final EventMediaService eventMediaService;
    private final TeamMemberService teamMemberService;
    
    public EventController(EventService eventService, EventMediaService eventMediaService, TeamMemberService teamMemberService) {
        this.eventService = eventService;
        this.eventMediaService = eventMediaService;
        this.teamMemberService = teamMemberService;
    }
    
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        EventResponse response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequest request) {
        EventResponse response = eventService.updateEvent(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{eventId}/media")
    public ResponseEntity<List<EventMediaResponse>> getEventMedia(
            @PathVariable Long eventId,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        List<EventMediaResponse> media;
        if (includeDeleted) {
            media = eventMediaService.getAllMediaByEventId(eventId, true);
        } else {
            media = eventMediaService.getMediaByEventId(eventId);
        }
        return ResponseEntity.ok(media);
    }
    
    @GetMapping("/team-members")
    public ResponseEntity<List<TeamMemberResponse>> getAllTeamMembers() {
        List<TeamMemberResponse> teamMembers = teamMemberService.getAllTeamMembers();
        return ResponseEntity.ok(teamMembers);
    }
}

