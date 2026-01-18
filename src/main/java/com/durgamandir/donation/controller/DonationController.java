package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.DonationRequest;
import com.durgamandir.donation.dto.DonationResponse;
import com.durgamandir.donation.dto.DonationStatsResponse;
import com.durgamandir.donation.dto.PaginatedDonationResponse;
import com.durgamandir.donation.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    
    private final DonationService donationService;
    
    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }
    
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@Valid @RequestBody DonationRequest request) {
        try {
            DonationResponse response = donationService.createDonation(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
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
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String thana,
            @RequestParam(required = false) String village) {
        PaginatedDonationResponse response = donationService.getPublicDonationsPaginated(
                page, size, name, stateId, district, thana, village);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<DonationStatsResponse> getDonationStats() {
        DonationStatsResponse stats = donationService.getStats();
        return ResponseEntity.ok(stats);
    }
}


