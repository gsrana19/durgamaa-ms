package com.durgamandir.booking.controller;

import com.durgamandir.booking.dto.*;
import com.durgamandir.booking.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServicesController {
    
    private final SpecialPujaService specialPujaService;
    private final MorningAartiVisitService morningAartiVisitService;
    private final AbhishekamBookingService abhishekamBookingService;
    private final FlowerOfferingService flowerOfferingService;
    
    public ServicesController(SpecialPujaService specialPujaService,
                             MorningAartiVisitService morningAartiVisitService,
                             AbhishekamBookingService abhishekamBookingService,
                             FlowerOfferingService flowerOfferingService) {
        this.specialPujaService = specialPujaService;
        this.morningAartiVisitService = morningAartiVisitService;
        this.abhishekamBookingService = abhishekamBookingService;
        this.flowerOfferingService = flowerOfferingService;
    }
    
    @PostMapping("/special-puja")
    public ResponseEntity<SpecialPujaResponse> createSpecialPuja(@Valid @RequestBody SpecialPujaRequest request) {
        SpecialPujaResponse response = specialPujaService.createSpecialPuja(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/morning-aarti")
    public ResponseEntity<MorningAartiVisitResponse> createMorningAartiVisit(@Valid @RequestBody MorningAartiVisitRequest request) {
        MorningAartiVisitResponse response = morningAartiVisitService.createMorningAartiVisit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/abhishekam")
    public ResponseEntity<AbhishekamBookingResponse> createAbhishekamBooking(@Valid @RequestBody AbhishekamBookingRequest request) {
        AbhishekamBookingResponse response = abhishekamBookingService.createAbhishekamBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/flowers")
    public ResponseEntity<FlowerOfferingResponse> createFlowerOffering(@Valid @RequestBody FlowerOfferingRequest request) {
        FlowerOfferingResponse response = flowerOfferingService.createFlowerOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}




