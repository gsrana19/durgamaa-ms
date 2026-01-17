package com.durgamandir.booking.repository;

import com.durgamandir.booking.model.AbhishekamBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbhishekamBookingRepository extends JpaRepository<AbhishekamBooking, Long> {
}




