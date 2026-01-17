package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Sankalpam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SankalpamRepository extends JpaRepository<Sankalpam, Long> {
}




