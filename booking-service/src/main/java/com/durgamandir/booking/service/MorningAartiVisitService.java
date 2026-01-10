package com.durgamandir.booking.service;

import com.durgamandir.booking.dto.MorningAartiVisitRequest;
import com.durgamandir.booking.dto.MorningAartiVisitResponse;
import com.durgamandir.booking.model.MorningAartiVisit;
import com.durgamandir.booking.repository.MorningAartiVisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MorningAartiVisitService {
    
    private final MorningAartiVisitRepository morningAartiVisitRepository;
    
    public MorningAartiVisitService(MorningAartiVisitRepository morningAartiVisitRepository) {
        this.morningAartiVisitRepository = morningAartiVisitRepository;
    }
    
    public MorningAartiVisitResponse createMorningAartiVisit(MorningAartiVisitRequest request) {
        // Validate visit date is not in the past
        if (request.getVisitDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Visit date cannot be in the past");
        }
        
        MorningAartiVisit visit = new MorningAartiVisit();
        visit.setName(request.getName());
        visit.setVisitDate(request.getVisitDate());
        visit.setFamilyMembers(request.getFamilyMembers());
        
        MorningAartiVisit saved = morningAartiVisitRepository.save(visit);
        return mapToResponse(saved);
    }
    
    private MorningAartiVisitResponse mapToResponse(MorningAartiVisit visit) {
        return new MorningAartiVisitResponse(
                visit.getId(),
                visit.getName(),
                visit.getVisitDate(),
                visit.getFamilyMembers(),
                visit.getCreatedAt()
        );
    }
}




