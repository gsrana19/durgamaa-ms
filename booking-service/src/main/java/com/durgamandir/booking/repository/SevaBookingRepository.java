package com.durgamandir.booking.repository;

import com.durgamandir.booking.model.SevaBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SevaBookingRepository extends JpaRepository<SevaBooking, Long> {
}




