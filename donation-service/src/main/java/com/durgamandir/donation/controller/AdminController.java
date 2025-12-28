package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.AdminSignupRequest;
import com.durgamandir.donation.dto.DonationRequest;
import com.durgamandir.donation.dto.DonationResponse;
import com.durgamandir.donation.dto.DonationStatsResponse;
import com.durgamandir.donation.dto.UpdateRequest;
import com.durgamandir.donation.dto.UpdateResponse;
import com.durgamandir.donation.service.DonationService;
import com.durgamandir.donation.service.UpdateService;
import com.durgamandir.donation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final DonationService donationService;
    private final UpdateService updateService;
    private final UserService userService;
    
    public AdminController(DonationService donationService, 
                           UpdateService updateService,
                           UserService userService) {
        this.donationService = donationService;
        this.updateService = updateService;
        this.userService = userService;
    }
    
    
    @GetMapping("/donations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonationResponse>> getAllDonations() {
        List<DonationResponse> donations = donationService.getAllDonations();
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
}

