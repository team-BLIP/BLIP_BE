package com.example.blip_be.domain.meeting.domain.repository;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.MeetingParticipation;
import com.example.blip_be.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Optional<Meeting> findById(Long meetingId);
}
