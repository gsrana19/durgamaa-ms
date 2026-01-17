package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.UpdateResponse;
import com.durgamandir.donation.service.UpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/updates")
@CrossOrigin(origins = "*")
public class UpdateController {
    
    private final UpdateService updateService;
    
    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }
    
    @GetMapping("/public")
    public ResponseEntity<List<UpdateResponse>> getPublicUpdates() {
        List<UpdateResponse> updates = updateService.getAllUpdates();
        return ResponseEntity.ok(updates);
    }
    
    @GetMapping("/public/latest-image")
    public ResponseEntity<UpdateResponse> getLatestUpdateWithImage() {
        UpdateResponse update = updateService.getLatestUpdateWithImage();
        if (update != null) {
            return ResponseEntity.ok(update);
        }
        return ResponseEntity.noContent().build();
    }
}





