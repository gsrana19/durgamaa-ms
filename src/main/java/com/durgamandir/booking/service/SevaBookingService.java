package com.durgamandir.booking.service;

import com.durgamandir.booking.dto.SevaBookingRequest;
import com.durgamandir.booking.dto.SevaBookingResponse;
import com.durgamandir.booking.model.SevaBooking;
import com.durgamandir.booking.repository.SevaBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class SevaBookingService {
    
    private final SevaBookingRepository sevaBookingRepository;
    
    public SevaBookingService(SevaBookingRepository sevaBookingRepository) {
        this.sevaBookingRepository = sevaBookingRepository;
    }
    
    public SevaBookingResponse createSevaBooking(SevaBookingRequest request) {
        // Validate booking date is not in the past
        if (request.getBookingDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking date cannot be in the past");
        }
        
        SevaBooking booking = new SevaBooking();
        booking.setSevaName(request.getSevaName());
        booking.setBookingDate(request.getBookingDate());
        booking.setDevoteeName(request.getDevoteeName());
        booking.setGotra(request.getGotra());
        booking.setPhoneOrEmail(request.getPhoneOrEmail());
        booking.setSpecialIntentions(request.getSpecialIntentions());
        
        SevaBooking saved = sevaBookingRepository.save(booking);
        return mapToResponse(saved);
    }
    
    private SevaBookingResponse mapToResponse(SevaBooking booking) {
        return new SevaBookingResponse(
                booking.getId(),
                booking.getSevaName(),
                booking.getBookingDate(),
                booking.getDevoteeName(),
                booking.getGotra(),
                booking.getPhoneOrEmail(),
                booking.getSpecialIntentions(),
                booking.getCreatedAt()
        );
    }
}

