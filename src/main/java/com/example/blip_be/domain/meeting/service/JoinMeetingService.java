package com.example.blip_be.domain.meeting.service;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.request.JoinMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.response.JoinMeetingResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinMeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Transactional
    public JoinMeetingResponse joinMeeting(JoinMeetingRequest request) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 회의"));

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (meetingRepository.existsByIdAndParticipantsContains(request.getMeetingId(), user)) {
            return new JoinMeetingResponse(meeting.getId(), user.getId(), "이미 참여한 회의");
        }

        meeting.getParticipants().add(user);
        meetingRepository.save(meeting);

        return new JoinMeetingResponse(meeting.getId(), user.getId(), "성공적으로 참가");
    }
}
