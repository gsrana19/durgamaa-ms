package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.SankalpamRequest;
import com.durgamandir.donation.dto.SankalpamResponse;
import com.durgamandir.donation.service.SankalpamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sankalpam")
public class SankalpamController {
    
    private final SankalpamService sankalpamService;
    
    public SankalpamController(SankalpamService sankalpamService) {
        this.sankalpamService = sankalpamService;
    }
    
    @PostMapping
    public ResponseEntity<SankalpamResponse> createSankalpam(@Valid @RequestBody SankalpamRequest request) {
        SankalpamResponse response = sankalpamService.createSankalpam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}




