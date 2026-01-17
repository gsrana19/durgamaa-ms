package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.EventMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventMediaRepository extends JpaRepository<EventMedia, Long> {
    List<EventMedia> findByEventId(Long eventId);
    List<EventMedia> findByEventIdAndDeletedFalse(Long eventId);
    List<EventMedia> findByEventIdAndDeletedTrue(Long eventId);
}

