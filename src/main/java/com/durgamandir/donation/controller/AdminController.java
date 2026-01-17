package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.AdminSignupRequest;
import com.durgamandir.donation.dto.DonationRequest;
import com.durgamandir.donation.dto.DonationResponse;
import com.durgamandir.donation.dto.DonationStatsResponse;
import com.durgamandir.donation.dto.EventMediaResponse;
import com.durgamandir.donation.dto.EventRequest;
import com.durgamandir.donation.dto.EventResponse;
import com.durgamandir.donation.dto.ExpenseRequest;
import com.durgamandir.donation.dto.ExpenseResponse;
import com.durgamandir.donation.dto.PaginatedExpenseResponse;
import com.durgamandir.donation.dto.UpdateRequest;
import com.durgamandir.donation.service.EventMediaService;
import com.durgamandir.donation.service.EventService;
import com.durgamandir.donation.dto.UpdateResponse;
import com.durgamandir.donation.dto.TeamMemberRequest;
import com.durgamandir.donation.dto.TeamMemberResponse;
import com.durgamandir.donation.service.TeamMemberService;
import org.springframework.web.multipart.MultipartFile;
import com.durgamandir.donation.service.DonationService;
import com.durgamandir.donation.service.ExpenseService;
import com.durgamandir.donation.service.UpdateService;
import com.durgamandir.donation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final DonationService donationService;
    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final UserService userService;
    private final EventMediaService eventMediaService;
    private final TeamMemberService teamMemberService;
    
    public AdminController(DonationService donationService, 
                           UpdateService updateService,
                           ExpenseService expenseService,
                           UserService userService,
                           EventMediaService eventMediaService,
                           TeamMemberService teamMemberService) {
        this.donationService = donationService;
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.userService = userService;
        this.eventMediaService = eventMediaService;
        this.teamMemberService = teamMemberService;
    }
    
    
    @GetMapping("/donations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonationResponse>> getAllDonations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String thana,
            @RequestParam(required = false) String village) {
        List<DonationResponse> donations = donationService.searchAllDonations(
                name, stateId, district, thana, village);
        return ResponseEntity.ok(donations);
    }
    
    @PatchMapping("/donations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDonation(
            @PathVariable Long id,
            @RequestBody DonationRequest request) {
        try {
            DonationResponse response = donationService.updateDonation(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonationStatsResponse> getAdminStats() {
        DonationStatsResponse stats = donationService.getStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/updates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UpdateResponse>> getAllUpdates() {
        List<UpdateResponse> updates = updateService.getAllUpdates();
        return ResponseEntity.ok(updates);
    }
    
    @PostMapping("/updates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateResponse> createUpdate(@Valid @RequestBody UpdateRequest request) {
        UpdateResponse response = updateService.createUpdate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/updates/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateResponse> updateUpdate(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRequest request) {
        UpdateResponse response = updateService.updateUpdate(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/updates/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUpdate(@PathVariable Long id) {
        updateService.deleteUpdate(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getExpenses(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String description) {
        // If pagination parameters are provided, return paginated response
        if (page != null && size != null) {
            PaginatedExpenseResponse response = expenseService.getExpensesPaginated(page, size, description);
            return ResponseEntity.ok(response);
        }
        // Otherwise, return all expenses (for backward compatibility)
        List<ExpenseResponse> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/expenses/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getExpenseStats() {
        BigDecimal totalDonations = donationService.getStats().getTotalAmount();
        BigDecimal totalExpenses = expenseService.getTotalExpenses();
        BigDecimal remaining = totalDonations.subtract(totalExpenses);
        // Ensure remaining is never negative
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            remaining = BigDecimal.ZERO;
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDonations", totalDonations);
        stats.put("totalExpenses", totalExpenses);
        stats.put("remaining", remaining);
        
        return ResponseEntity.ok(stats);
    }
    
    @PostMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/expenses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/expenses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }
    
    // Event Media Endpoints
    @PostMapping("/events/{eventId}/media")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventMediaResponse> uploadEventMedia(
            @PathVariable Long eventId,
            @RequestParam("file") MultipartFile file) {
        try {
            EventMediaResponse response = eventMediaService.uploadMedia(eventId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/events/{eventId}/media/{mediaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEventMedia(
            @PathVariable Long eventId,
            @PathVariable Long mediaId) {
        try {
            eventMediaService.deleteMedia(mediaId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/events/{eventId}/media/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventMediaResponse>> getDeletedEventMedia(@PathVariable Long eventId) {
        List<EventMediaResponse> deletedMedia = eventMediaService.getDeletedMediaByEventId(eventId);
        return ResponseEntity.ok(deletedMedia);
    }
    
    @PostMapping("/events/{eventId}/media/{mediaId}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventMediaResponse> restoreEventMedia(
            @PathVariable Long eventId,
            @PathVariable Long mediaId) {
        try {
            eventMediaService.restoreMedia(mediaId);
            EventMediaResponse response = eventMediaService.getMediaByEventId(eventId)
                    .stream()
                    .filter(m -> m.getId().equals(mediaId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Media not found after restore"));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/events/{eventId}/media/{mediaId}/permanent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> permanentlyDeleteEventMedia(
            @PathVariable Long eventId,
            @PathVariable Long mediaId) {
        try {
            eventMediaService.permanentlyDeleteMedia(mediaId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Team Member Endpoints
    @PostMapping("/team-members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamMemberResponse> createTeamMember(
            @RequestParam("name") String name,
            @RequestParam("position") String position,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
            @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            TeamMemberRequest request = new TeamMemberRequest();
            request.setName(name);
            request.setPosition(position);
            request.setMobileNumber(mobileNumber);
            request.setDisplayOrder(displayOrder != null ? displayOrder : 0);
            
            TeamMemberResponse response = teamMemberService.createTeamMember(request, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/team-members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeamMemberResponse>> getAllTeamMembers() {
        List<TeamMemberResponse> teamMembers = teamMemberService.getAllTeamMembers();
        return ResponseEntity.ok(teamMembers);
    }
    
    @PutMapping("/team-members/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamMemberResponse> updateTeamMember(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("position") String position,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
            @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            TeamMemberRequest request = new TeamMemberRequest();
            request.setName(name);
            request.setPosition(position);
            request.setMobileNumber(mobileNumber);
            request.setDisplayOrder(displayOrder);
            
            TeamMemberResponse response = teamMemberService.updateTeamMember(id, request, imageFile);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/team-members/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTeamMember(@PathVariable Long id) {
        try {
            teamMemberService.deleteTeamMember(id);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete team member"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

