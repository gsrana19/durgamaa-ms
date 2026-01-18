package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.DonationConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationConfirmationRepository extends JpaRepository<DonationConfirmation, Long> {
    List<DonationConfirmation> findByStatusOrderByCreatedAtDesc(DonationConfirmation.Status status);
    List<DonationConfirmation> findAllByOrderByCreatedAtDesc();
    boolean existsByUtr(String utr);
}


