package com.example.blip_be.domain.meeting.domain.repository;

import com.example.blip_be.domain.meeting.domain.MeetingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingSummaryRepository extends JpaRepository<MeetingSummary, Long> {

    Optional<MeetingSummary> findById(Long id);
    List<MeetingSummary> findAll();
}
