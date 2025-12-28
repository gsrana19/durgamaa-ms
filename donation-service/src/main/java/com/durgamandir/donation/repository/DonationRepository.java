package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByShowPublicTrueOrderByCreatedAtDesc();
    
    Page<Donation> findByShowPublicTrueOrderByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT SUM(d.amount) FROM Donation d")
    BigDecimal getTotalAmount();
    
    @Query("SELECT COUNT(d) FROM Donation d")
    Long getTotalDonors();
    
    @Query("SELECT COUNT(d) FROM Donation d WHERE d.showPublic = true")
    Long getTotalPublicDonors();
    
    @Query("SELECT COUNT(d) FROM Donation d WHERE d.createdAt >= :since")
    Long countByCreatedAtAfter(LocalDateTime since);
}

