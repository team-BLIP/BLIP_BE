package com.example.blip_be.domain.meeting.domain.repository;

import com.example.blip_be.domain.meeting.domain.MeetingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingFeedbackRepository extends JpaRepository<MeetingFeedback, Long> {

    List<MeetingFeedback> findAllByMeeting_Team_Id(Long teamId);
}
