package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByStateId(Long stateId);
    Optional<District> findByNameAndStateId(String name, Long stateId);
}


