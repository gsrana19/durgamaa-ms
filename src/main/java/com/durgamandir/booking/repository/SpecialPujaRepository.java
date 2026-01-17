package com.durgamandir.booking.repository;

import com.durgamandir.booking.model.SpecialPuja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialPujaRepository extends JpaRepository<SpecialPuja, Long> {
}




