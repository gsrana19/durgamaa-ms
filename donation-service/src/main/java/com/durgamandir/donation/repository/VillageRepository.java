package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    List<Village> findByThanaIdAndActiveTrue(Long thanaId);
    Optional<Village> findByNameAndThanaId(String name, Long thanaId);
}

