package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Thana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThanaRepository extends JpaRepository<Thana, Long> {
    List<Thana> findByDistrictId(Long districtId);
    Optional<Thana> findByNameAndDistrictId(String name, Long districtId);
}

