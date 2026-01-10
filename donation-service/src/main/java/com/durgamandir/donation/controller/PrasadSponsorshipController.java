package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.PrasadSponsorshipRequest;
import com.durgamandir.donation.dto.PrasadSponsorshipResponse;
import com.durgamandir.donation.service.PrasadSponsorshipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prasad-sponsorships")
public class PrasadSponsorshipController {
    
    private final PrasadSponsorshipService prasadSponsorshipService;
    
    public PrasadSponsorshipController(PrasadSponsorshipService prasadSponsorshipService) {
        this.prasadSponsorshipService = prasadSponsorshipService;
    }
    
    @PostMapping
    public ResponseEntity<PrasadSponsorshipResponse> createPrasadSponsorship(@Valid @RequestBody PrasadSponsorshipRequest request) {
        PrasadSponsorshipResponse response = prasadSponsorshipService.createPrasadSponsorship(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}




