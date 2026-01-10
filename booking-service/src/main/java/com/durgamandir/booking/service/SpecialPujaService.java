package com.durgamandir.booking.service;

import com.durgamandir.booking.dto.SpecialPujaRequest;
import com.durgamandir.booking.dto.SpecialPujaResponse;
import com.durgamandir.booking.model.SpecialPuja;
import com.durgamandir.booking.repository.SpecialPujaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class SpecialPujaService {
    
    private final SpecialPujaRepository specialPujaRepository;
    
    public SpecialPujaService(SpecialPujaRepository specialPujaRepository) {
        this.specialPujaRepository = specialPujaRepository;
    }
    
    public SpecialPujaResponse createSpecialPuja(SpecialPujaRequest request) {
        // Validate preferred date is not in the past
        if (request.getPreferredDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Preferred date cannot be in the past");
        }
        
        SpecialPuja specialPuja = new SpecialPuja();
        specialPuja.setPujaType(request.getPujaType());
        specialPuja.setDevoteeName(request.getDevoteeName());
        specialPuja.setGotra(request.getGotra());
        specialPuja.setCity(request.getCity());
        specialPuja.setPreferredDate(request.getPreferredDate());
        specialPuja.setTimeSlot(request.getTimeSlot());
        specialPuja.setIntention(request.getIntention());
        
        SpecialPuja saved = specialPujaRepository.save(specialPuja);
        return mapToResponse(saved);
    }
    
    private SpecialPujaResponse mapToResponse(SpecialPuja specialPuja) {
        return new SpecialPujaResponse(
                specialPuja.getId(),
                specialPuja.getPujaType(),
                specialPuja.getDevoteeName(),
                specialPuja.getGotra(),
                specialPuja.getCity(),
                specialPuja.getPreferredDate(),
                specialPuja.getTimeSlot(),
                specialPuja.getIntention(),
                specialPuja.getStatus(),
                specialPuja.getCreatedAt()
        );
    }
}




