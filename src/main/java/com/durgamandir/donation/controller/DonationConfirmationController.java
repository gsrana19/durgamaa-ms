package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.DonationConfirmationRequest;
import com.durgamandir.donation.dto.DonationConfirmationResponse;
import com.durgamandir.donation.service.DonationConfirmationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
public class DonationConfirmationController {
    
    private final DonationConfirmationService confirmationService;
    
    @Autowired
    public DonationConfirmationController(DonationConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }
    
    @PostMapping("/confirm")
    public ResponseEntity<DonationConfirmationResponse> confirmDonation(
            @Valid @RequestBody DonationConfirmationRequest request) {
        try {
            DonationConfirmationResponse response = confirmationService.createConfirmation(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

