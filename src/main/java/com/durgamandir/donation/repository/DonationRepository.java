package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    // Search query with location subqueries (public only)
    @Query("SELECT d FROM Donation d " +
           "WHERE d.showPublic = true " +
           "AND (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:stateId IS NULL OR d.stateId = :stateId) " +
           "AND (:district IS NULL OR :district = '' OR EXISTS " +
           "     (SELECT 1 FROM District dist WHERE dist.id = d.districtId AND LOWER(dist.name) LIKE LOWER(CONCAT('%', :district, '%')))) " +
           "AND (:thana IS NULL OR :thana = '' OR EXISTS " +
           "     (SELECT 1 FROM Thana t WHERE t.id = d.thanaId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :thana, '%')))) " +
           "AND (:village IS NULL OR :village = '' OR EXISTS " +
           "     (SELECT 1 FROM Village v WHERE v.id = d.villageId AND LOWER(v.name) LIKE LOWER(CONCAT('%', :village, '%'))) " +
           "     OR (d.customVillageName IS NOT NULL AND LOWER(d.customVillageName) LIKE LOWER(CONCAT('%', :village, '%')))) " +
           "ORDER BY d.createdAt DESC")
    Page<Donation> searchPublicDonations(
            @Param("name") String name,
            @Param("stateId") Long stateId,
            @Param("district") String district,
            @Param("thana") String thana,
            @Param("village") String village,
            Pageable pageable);
    
    // Search query for ALL donations (admin use)
    @Query("SELECT d FROM Donation d " +
           "WHERE (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:stateId IS NULL OR d.stateId = :stateId) " +
           "AND (:district IS NULL OR :district = '' OR EXISTS " +
           "     (SELECT 1 FROM District dist WHERE dist.id = d.districtId AND LOWER(dist.name) LIKE LOWER(CONCAT('%', :district, '%')))) " +
           "AND (:thana IS NULL OR :thana = '' OR EXISTS " +
           "     (SELECT 1 FROM Thana t WHERE t.id = d.thanaId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :thana, '%')))) " +
           "AND (:village IS NULL OR :village = '' OR EXISTS " +
           "     (SELECT 1 FROM Village v WHERE v.id = d.villageId AND LOWER(v.name) LIKE LOWER(CONCAT('%', :village, '%'))) " +
           "     OR (d.customVillageName IS NOT NULL AND LOWER(d.customVillageName) LIKE LOWER(CONCAT('%', :village, '%')))) " +
           "ORDER BY d.createdAt DESC")
    List<Donation> searchAllDonations(
            @Param("name") String name,
            @Param("stateId") Long stateId,
            @Param("district") String district,
            @Param("thana") String thana,
            @Param("village") String village);
}

