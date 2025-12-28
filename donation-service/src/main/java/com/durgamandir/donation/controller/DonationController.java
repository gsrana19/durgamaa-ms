package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.DonationRequest;
import com.durgamandir.donation.dto.DonationResponse;
import com.durgamandir.donation.dto.DonationStatsResponse;
import com.durgamandir.donation.dto.PaginatedDonationResponse;
import com.durgamandir.donation.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {
    
    private final DonationService donationService;
    
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }
    
    @PostMapping
    public ResponseEntity<?> createDonation(@Valid @RequestBody DonationRequest request) {
        try {
            DonationResponse response = donationService.createDonation(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/public")
    public ResponseEntity<List<DonationResponse>> getPublicDonations() {
        List<DonationResponse> donations = donationService.getPublicDonations();
        return ResponseEntity.ok(donations);
    }
    
    @GetMapping("/public/paginated")
    public ResponseEntity<PaginatedDonationResponse> getPublicDonationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PaginatedDonationResponse response = donationService.getPublicDonationsPaginated(page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<DonationStatsResponse> getStats() {
        DonationStatsResponse stats = donationService.getStats();
        return ResponseEntity.ok(stats);
    }
}

