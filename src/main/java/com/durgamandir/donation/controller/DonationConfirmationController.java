package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.DonationConfirmationRequest;
import com.durgamandir.donation.dto.DonationConfirmationResponse;
import com.durgamandir.donation.service.DonationConfirmationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/donation-confirmations")
public class DonationConfirmationController {
    
    private static final String UPLOAD_DIR = "uploads/";
    
    private final DonationConfirmationService confirmationService;
    
    // Initialize upload directory
    static {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to create upload directory: " + e.getMessage());
        }
    }
    
    @Autowired
    public DonationConfirmationController(DonationConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }
    
    @PostMapping
    public ResponseEntity<?> confirmDonation(
            @Valid @RequestBody DonationConfirmationRequest request) {
        try {
            DonationConfirmationResponse response = confirmationService.createConfirmation(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    
    /**
     * Public endpoint for uploading transaction screenshots (payment confirmations)
     * No authentication required as this is used by public users
     */
    @PostMapping("/upload-screenshot")
    public ResponseEntity<?> uploadPaymentScreenshot(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }
            
            // Validate file type - only images allowed
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Only image files are allowed"));
            }
            
            // Validate file size (max 5MB)
            long maxSize = 5 * 1024 * 1024; // 5MB
            if (file.getSize() > maxSize) {
                return ResponseEntity.badRequest().body(Map.of("error", "File size must be less than 5MB"));
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = "payment_" + UUID.randomUUID().toString() + extension;
            
            // Save file
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Return URL (relative path that can be served as static resource)
            String imageUrl = "/uploads/" + filename;
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);
            response.put("filename", filename);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            System.err.println("Error uploading payment screenshot: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload screenshot"));
        }
    }
}

