package com.durgamandir.booking.service;

import com.durgamandir.booking.dto.FlowerOfferingRequest;
import com.durgamandir.booking.dto.FlowerOfferingResponse;
import com.durgamandir.booking.model.FlowerOffering;
import com.durgamandir.booking.repository.FlowerOfferingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class FlowerOfferingService {
    
    private final FlowerOfferingRepository flowerOfferingRepository;
    
    public FlowerOfferingService(FlowerOfferingRepository flowerOfferingRepository) {
        this.flowerOfferingRepository = flowerOfferingRepository;
    }
    
    public FlowerOfferingResponse createFlowerOffering(FlowerOfferingRequest request) {
        // Validate date is not in the past
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
        
        FlowerOffering offering = new FlowerOffering();
        offering.setName(request.getName());
        offering.setDate(request.getDate());
        offering.setFlowerType(request.getFlowerType());
        
        FlowerOffering saved = flowerOfferingRepository.save(offering);
        return mapToResponse(saved);
    }
    
    private FlowerOfferingResponse mapToResponse(FlowerOffering offering) {
        return new FlowerOfferingResponse(
                offering.getId(),
                offering.getName(),
                offering.getDate(),
                offering.getFlowerType(),
                offering.getCreatedAt()
        );
    }
}




