package com.example.blip_be.domain.meeting.service;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.MeetingParticipation;
import com.example.blip_be.domain.meeting.domain.repository.MeetingParticipationRepository;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.request.LeaveMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.response.LeaveMeetingResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveMeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingParticipationRepository meetingParticipationRepository;
    private final UserRepository userRepository;

    @Transactional
    public LeaveMeetingResponse leaveMeeting(LeaveMeetingRequest request) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(()-> new IllegalArgumentException("회의를 찾을 수 없음"));

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        MeetingParticipation participation = meetingParticipationRepository.findByMeetingAndUser(meeting, user)
                .orElseThrow(()-> new IllegalArgumentException("회의 참여 기록을 찾을 수 없음"));

        meetingParticipationRepository.delete(participation);

        return new LeaveMeetingResponse(meeting.getId(), user.getId(), "회의에서 나감");
    }
}
