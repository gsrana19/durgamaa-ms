package com.durgamandir.booking.controller;

import com.durgamandir.booking.dto.SevaBookingRequest;
import com.durgamandir.booking.dto.SevaBookingResponse;
import com.durgamandir.booking.service.SevaBookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seva-bookings")
public class SevaBookingController {
    
    private final SevaBookingService sevaBookingService;
    
    public SevaBookingController(SevaBookingService sevaBookingService) {
        this.sevaBookingService = sevaBookingService;
    }
    
    @PostMapping
    public ResponseEntity<SevaBookingResponse> createSevaBooking(@Valid @RequestBody SevaBookingRequest request) {
        SevaBookingResponse response = sevaBookingService.createSevaBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

