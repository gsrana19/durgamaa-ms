package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.DonationConfirmationRequest;
import com.durgamandir.donation.dto.DonationConfirmationResponse;
import com.durgamandir.donation.dto.VerifyDonationRequest;
import com.durgamandir.donation.entity.DonationConfirmation;
import com.durgamandir.donation.repository.DonationConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationConfirmationService {
    
    private final DonationConfirmationRepository repository;
    
    @Autowired
    public DonationConfirmationService(DonationConfirmationRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public DonationConfirmationResponse createConfirmation(DonationConfirmationRequest request) {
        // Validate mobile number
        if (request.getMobile() == null || request.getMobile().trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile number is required");
        }
        
        // Clean mobile number (remove non-digits)
        String cleanMobile = request.getMobile().replaceAll("[^0-9]", "");
        if (cleanMobile.length() != 10 || !cleanMobile.matches("^[6-9]\\d{9}$")) {
            throw new IllegalArgumentException("Mobile number must be 10 digits starting with 6-9");
        }
        
        // Check if UTR already exists
        if (repository.existsByUtr(request.getUtr())) {
            throw new IllegalArgumentException("UTR already exists. Please check your UTR/Transaction ID.");
        }
        
        DonationConfirmation confirmation = new DonationConfirmation();
        confirmation.setName(request.getName());
        confirmation.setMobile(cleanMobile);
        confirmation.setAmount(request.getAmount());
        confirmation.setMethod(request.getMethod());
        confirmation.setUtr(request.getUtr());
        confirmation.setMessage(request.getMessage());
        confirmation.setStatus(DonationConfirmation.Status.PENDING);
        
        DonationConfirmation saved = repository.save(confirmation);
        return new DonationConfirmationResponse(saved);
    }
    
    public List<DonationConfirmationResponse> getConfirmationsByStatus(DonationConfirmation.Status status) {
        List<DonationConfirmation> confirmations = repository.findByStatusOrderByCreatedAtDesc(status);
        return confirmations.stream()
                .map(DonationConfirmationResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<DonationConfirmationResponse> getAllConfirmations() {
        List<DonationConfirmation> confirmations = repository.findAllByOrderByCreatedAtDesc();
        return confirmations.stream()
                .map(DonationConfirmationResponse::new)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public DonationConfirmationResponse verifyConfirmation(Long id, String verifiedBy, VerifyDonationRequest request) {
        // Validate admin note is required
        if (request.getAdminNote() == null || request.getAdminNote().trim().isEmpty()) {
            throw new IllegalArgumentException("Admin note is required");
        }
        
        DonationConfirmation confirmation = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donation confirmation not found"));
        
        if (confirmation.getStatus() != DonationConfirmation.Status.PENDING) {
            throw new IllegalStateException("Only PENDING confirmations can be verified");
        }
        
        confirmation.setStatus(DonationConfirmation.Status.VERIFIED);
        confirmation.setVerifiedAt(LocalDateTime.now());
        confirmation.setVerifiedBy(verifiedBy);
        confirmation.setAdminNote(request.getAdminNote());
        
        DonationConfirmation saved = repository.save(confirmation);
        return new DonationConfirmationResponse(saved);
    }
    
    @Transactional
    public DonationConfirmationResponse rejectConfirmation(Long id, String verifiedBy, VerifyDonationRequest request) {
        // Validate admin note is required
        if (request.getAdminNote() == null || request.getAdminNote().trim().isEmpty()) {
            throw new IllegalArgumentException("Admin note is required");
        }
        
        DonationConfirmation confirmation = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donation confirmation not found"));
        
        if (confirmation.getStatus() != DonationConfirmation.Status.PENDING) {
            throw new IllegalStateException("Only PENDING confirmations can be rejected");
        }
        
        confirmation.setStatus(DonationConfirmation.Status.REJECTED);
        confirmation.setVerifiedAt(LocalDateTime.now());
        confirmation.setVerifiedBy(verifiedBy);
        confirmation.setAdminNote(request.getAdminNote());
        
        DonationConfirmation saved = repository.save(confirmation);
        return new DonationConfirmationResponse(saved);
    }
    
    public DonationConfirmationResponse getConfirmationById(Long id) {
        DonationConfirmation confirmation = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donation confirmation not found"));
        return new DonationConfirmationResponse(confirmation);
    }
}

