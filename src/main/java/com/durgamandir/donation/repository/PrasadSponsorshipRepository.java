package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.PrasadSponsorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrasadSponsorshipRepository extends JpaRepository<PrasadSponsorship, Long> {
}




