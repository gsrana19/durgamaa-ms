package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.DonationRequest;
import com.durgamandir.donation.dto.DonationResponse;
import com.durgamandir.donation.dto.DonationStatsResponse;
import com.durgamandir.donation.dto.PaginatedDonationResponse;
import com.durgamandir.donation.entity.Donation;
import com.durgamandir.donation.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final LocationService locationService;
    
    @Value("${donation.target-amount:5000000}")
    private BigDecimal targetAmount;
    
    public DonationService(DonationRepository donationRepository, LocationService locationService) {
        this.donationRepository = donationRepository;
        this.locationService = locationService;
    }
    
    public DonationResponse createDonation(DonationRequest request) {
        // Validation: Either villageId or customVillageName must be provided
        if (request.getVillageId() == null && 
            (request.getCustomVillageName() == null || request.getCustomVillageName().trim().isEmpty())) {
            throw new IllegalArgumentException("Either villageId or customVillageName must be provided");
        }
        
        Donation donation = new Donation();
        donation.setName(request.getName());
        donation.setEmail(request.getEmail());
        donation.setPhone(request.getPhone());
        donation.setAmount(request.getAmount());
        
        // Set location IDs
        donation.setCountryId(request.getCountryId());
        donation.setStateId(request.getStateId());
        donation.setDistrictId(request.getDistrictId());
        donation.setThanaId(request.getThanaId());
        donation.setVillageId(request.getVillageId());
        donation.setCustomVillageName(request.getCustomVillageName() != null && !request.getCustomVillageName().trim().isEmpty()
            ? request.getCustomVillageName().trim() : null);
        
        // Set city from district name for backward compatibility
        String districtName = locationService.getDistrictName(request.getDistrictId());
        donation.setCity(districtName);
        
        donation.setShowPublic(request.getShowPublic() != null ? request.getShowPublic() : true);
        
        Donation saved = donationRepository.save(donation);
        return mapToResponse(saved);
    }
    
    public List<DonationResponse> getPublicDonations() {
        return donationRepository.findByShowPublicTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public PaginatedDonationResponse getPublicDonationsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Donation> donationPage = donationRepository.findByShowPublicTrueOrderByCreatedAtDesc(pageable);
        
        List<DonationResponse> donations = donationPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return new PaginatedDonationResponse(
                donations,
                donationPage.getNumber(),
                donationPage.getTotalPages(),
                donationPage.getTotalElements(),
                donationPage.getSize()
        );
    }
    
    public List<DonationResponse> getAllDonations() {
        return donationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public DonationResponse updateDonation(Long id, DonationRequest request) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        
        if (request.getName() != null) donation.setName(request.getName());
        if (request.getEmail() != null) donation.setEmail(request.getEmail());
        if (request.getPhone() != null) donation.setPhone(request.getPhone());
        if (request.getAmount() != null) donation.setAmount(request.getAmount());
        if (request.getCountryId() != null) donation.setCountryId(request.getCountryId());
        if (request.getStateId() != null) donation.setStateId(request.getStateId());
        if (request.getDistrictId() != null) {
            donation.setDistrictId(request.getDistrictId());
            donation.setCity(locationService.getDistrictName(request.getDistrictId()));
        }
        if (request.getThanaId() != null) donation.setThanaId(request.getThanaId());
        if (request.getVillageId() != null) donation.setVillageId(request.getVillageId());
        if (request.getCustomVillageName() != null) donation.setCustomVillageName(request.getCustomVillageName());
        if (request.getShowPublic() != null) donation.setShowPublic(request.getShowPublic());
        
        Donation updated = donationRepository.save(donation);
        return mapToResponse(updated);
    }
    
    public DonationStatsResponse getStats() {
        BigDecimal totalAmount = donationRepository.getTotalAmount();
        // Use public donors count to match the public donor list
        Long totalDonors = donationRepository.getTotalPublicDonors();
        Long last7DaysCount = donationRepository.countByCreatedAtAfter(
                LocalDateTime.now().minusDays(7));
        
        return new DonationStatsResponse(totalAmount, totalDonors, last7DaysCount, targetAmount);
    }
    
    private DonationResponse mapToResponse(Donation donation) {
        // Resolve location names from IDs
        String countryName = locationService.getCountryName(donation.getCountryId());
        String stateName = locationService.getStateName(donation.getStateId());
        String districtName = locationService.getDistrictName(donation.getDistrictId());
        String thanaName = locationService.getThanaName(donation.getThanaId());
        String villageName = donation.getVillageId() != null 
            ? locationService.getVillageName(donation.getVillageId())
            : null;
        
        return new DonationResponse(
                donation.getId(),
                donation.getName(),
                donation.getEmail(),
                donation.getPhone(),
                donation.getAmount(),
                donation.getCountryId(),
                countryName,
                donation.getStateId(),
                stateName,
                donation.getDistrictId(),
                districtName,
                donation.getThanaId(),
                thanaName,
                donation.getVillageId(),
                villageName,
                donation.getCustomVillageName(),
                donation.getShowPublic(),
                donation.getCreatedAt()
        );
    }
}

