package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.DonationConfirmationRequest;
import com.durgamandir.donation.dto.DonationConfirmationResponse;
import com.durgamandir.donation.dto.DonationRequest;
import com.durgamandir.donation.dto.VerifyDonationRequest;
import com.durgamandir.donation.entity.Donation;
import com.durgamandir.donation.entity.DonationConfirmation;
import com.durgamandir.donation.repository.DonationConfirmationRepository;
import com.durgamandir.donation.repository.DonationRepository;
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
    private final DonationService donationService;
    private final LocationService locationService;
    
    @Autowired
    public DonationConfirmationService(DonationConfirmationRepository repository,
                                      DonationService donationService,
                                      LocationService locationService) {
        this.repository = repository;
        this.donationService = donationService;
        this.locationService = locationService;
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
        
        // Validate: Either UTR or transactionScreenshot must be provided
        String utr = request.getUtr() != null ? request.getUtr().trim() : null;
        String transactionScreenshot = request.getTransactionScreenshot() != null ? request.getTransactionScreenshot().trim() : null;
        
        if ((utr == null || utr.isEmpty()) && (transactionScreenshot == null || transactionScreenshot.isEmpty())) {
            throw new IllegalArgumentException("Either UTR/Transaction ID or Transaction Screenshot must be provided");
        }
        
        // Check if UTR already exists (only if UTR is provided)
        if (utr != null && !utr.isEmpty() && repository.existsByUtr(utr)) {
            throw new IllegalArgumentException("UTR already exists. Please check your UTR/Transaction ID.");
        }
        
        DonationConfirmation confirmation = new DonationConfirmation();
        confirmation.setName(request.getName());
        confirmation.setMobile(cleanMobile);
        confirmation.setAmount(request.getAmount());
        confirmation.setMethod(request.getMethod());
        confirmation.setUtr(utr); // Can be null if transactionScreenshot is provided
        confirmation.setTransactionScreenshot(transactionScreenshot); // Can be null if utr is provided
        confirmation.setMessage(request.getMessage());
        confirmation.setPurpose(request.getPurpose()); // Store purpose (Donation, Seva Booking, etc.)
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
        
        // Auto-create Donation record when payment is verified (affects stats immediately)
        // This applies to all verified payments (donations, seva bookings, etc.)
        createDonationFromConfirmation(saved);
        
        return new DonationConfirmationResponse(saved);
    }
    
    /**
     * Create a Donation record from verified DonationConfirmation
     * This ensures the donation appears in stats (total collected, devotee count)
     */
    private void createDonationFromConfirmation(DonationConfirmation confirmation) {
        try {
            // Get default location IDs (India -> Jharkhand -> Hazaribag -> Ichak -> Mangura)
            Long[] locationIds = locationService.getDefaultLocationIds();
            if (locationIds == null || locationIds.length < 4) {
                // Log error but don't fail verification
                System.err.println("Warning: Could not find default location IDs. Donation not created from confirmation ID: " + confirmation.getId());
                return;
            }
            
            Long countryId = locationIds[0];
            Long stateId = locationIds[1];
            Long districtId = locationIds[2];
            Long thanaId = locationIds[3];
            Long villageId = locationIds.length > 4 ? locationIds[4] : null;
            
            // Create DonationRequest from DonationConfirmation
            DonationRequest donationRequest = new DonationRequest();
            donationRequest.setName(confirmation.getName() != null ? confirmation.getName() : "Anonymous");
            donationRequest.setEmail(null); // Email not available in payment confirmation
            donationRequest.setPhone(confirmation.getMobile() != null ? confirmation.getMobile() : "");
            donationRequest.setAmount(confirmation.getAmount());
            donationRequest.setCountryId(countryId);
            donationRequest.setStateId(stateId);
            donationRequest.setDistrictId(districtId);
            donationRequest.setThanaId(thanaId);
            donationRequest.setVillageId(villageId);
            donationRequest.setCustomVillageName(villageId == null ? "Mangura" : null);
            donationRequest.setShowPublic(true);
            
            // Create donation record
            donationService.createDonation(donationRequest);
        } catch (Exception e) {
            // Log error but don't fail verification
            System.err.println("Error creating donation from confirmation ID: " + confirmation.getId() + ", Error: " + e.getMessage());
            e.printStackTrace();
        }
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

