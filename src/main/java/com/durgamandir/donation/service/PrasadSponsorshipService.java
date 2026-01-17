package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.PrasadSponsorshipRequest;
import com.durgamandir.donation.dto.PrasadSponsorshipResponse;
import com.durgamandir.donation.entity.PrasadSponsorship;
import com.durgamandir.donation.repository.PrasadSponsorshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class PrasadSponsorshipService {
    
    private final PrasadSponsorshipRepository prasadSponsorshipRepository;
    
    public PrasadSponsorshipService(PrasadSponsorshipRepository prasadSponsorshipRepository) {
        this.prasadSponsorshipRepository = prasadSponsorshipRepository;
    }
    
    public PrasadSponsorshipResponse createPrasadSponsorship(PrasadSponsorshipRequest request) {
        // Validate preferred date is not in the past
        if (request.getPreferredDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Preferred date cannot be in the past");
        }
        
        PrasadSponsorship sponsorship = new PrasadSponsorship();
        sponsorship.setName(request.getName());
        sponsorship.setOccasion(request.getOccasion());
        sponsorship.setPreferredDate(request.getPreferredDate());
        
        PrasadSponsorship saved = prasadSponsorshipRepository.save(sponsorship);
        return mapToResponse(saved);
    }
    
    private PrasadSponsorshipResponse mapToResponse(PrasadSponsorship sponsorship) {
        return new PrasadSponsorshipResponse(
                sponsorship.getId(),
                sponsorship.getName(),
                sponsorship.getOccasion(),
                sponsorship.getPreferredDate(),
                sponsorship.getCreatedAt()
        );
    }
}




