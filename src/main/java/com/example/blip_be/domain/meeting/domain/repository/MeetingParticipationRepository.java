package com.example.blip_be.domain.meeting.domain.repository;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.MeetingParticipation;
import com.example.blip_be.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingParticipationRepository extends JpaRepository<MeetingParticipation, Long> {

    Optional<MeetingParticipation> findByMeetingAndUser(Meeting meeting, UserEntity user);

    boolean existsByMeetingAndUser(Meeting meeting, UserEntity user);
}
