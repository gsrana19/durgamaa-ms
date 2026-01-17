package com.durgamandir.booking.service;

import com.durgamandir.booking.dto.AbhishekamBookingRequest;
import com.durgamandir.booking.dto.AbhishekamBookingResponse;
import com.durgamandir.booking.model.AbhishekamBooking;
import com.durgamandir.booking.repository.AbhishekamBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class AbhishekamBookingService {
    
    private final AbhishekamBookingRepository abhishekamBookingRepository;
    
    public AbhishekamBookingService(AbhishekamBookingRepository abhishekamBookingRepository) {
        this.abhishekamBookingRepository = abhishekamBookingRepository;
    }
    
    public AbhishekamBookingResponse createAbhishekamBooking(AbhishekamBookingRequest request) {
        // Validate preferred date is not in the past
        if (request.getPreferredDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Preferred date cannot be in the past");
        }
        
        AbhishekamBooking booking = new AbhishekamBooking();
        booking.setName(request.getName());
        booking.setGotra(request.getGotra());
        booking.setPreferredDate(request.getPreferredDate());
        booking.setFamilyMembers(request.getFamilyMembers());
        
        AbhishekamBooking saved = abhishekamBookingRepository.save(booking);
        return mapToResponse(saved);
    }
    
    private AbhishekamBookingResponse mapToResponse(AbhishekamBooking booking) {
        return new AbhishekamBookingResponse(
                booking.getId(),
                booking.getName(),
                booking.getGotra(),
                booking.getPreferredDate(),
                booking.getFamilyMembers(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }
}




