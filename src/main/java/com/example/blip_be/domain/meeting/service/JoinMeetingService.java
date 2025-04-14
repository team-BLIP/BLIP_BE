package com.example.blip_be.domain.meeting.service;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.response.JoinMeetingResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.UserNotFoundException;
import com.example.blip_be.domain.user.facade.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinMeetingService {

    private final MeetingRepository meetingRepository;
    private final UserFacade userFacade;

    @Transactional
    public JoinMeetingResponse joinMeeting(Long meetingId) {

        UserEntity user = userFacade.getCurrentUser();

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 회의"));

        if (meetingRepository.existsByIdAndParticipantsContains(meetingId, user)) {
            return new JoinMeetingResponse("이미 참여한 회의");
        }

        meeting.getParticipants().add(user);
        meetingRepository.save(meeting);

        return new JoinMeetingResponse("성공적으로 참가");
    }
}