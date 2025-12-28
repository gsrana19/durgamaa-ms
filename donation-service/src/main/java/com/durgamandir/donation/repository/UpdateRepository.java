package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateRepository extends JpaRepository<Update, Long> {
    List<Update> findAllByOrderByCreatedAtDesc();
}


