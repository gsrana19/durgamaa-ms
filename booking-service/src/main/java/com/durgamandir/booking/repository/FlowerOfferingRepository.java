package com.durgamandir.booking.repository;

import com.durgamandir.booking.model.FlowerOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerOfferingRepository extends JpaRepository<FlowerOffering, Long> {
}




