package com.example.blip_be.domain.meeting.service;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.request.StartMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.response.StartMeetingResponse;
import com.example.blip_be.domain.team.TeamNotFoundException;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StartMeetingService {

    private final MeetingRepository meetingRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public StartMeetingResponse startMeeting(StartMeetingRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        if (!team.getLeader().getId().equals(request.getLeaderId())) {
            throw new IllegalArgumentException("회의를 시작할 권한이 없습니다.");
        }

        Meeting meeting = Meeting.builder()
                .topic(request.getTopic())
                .team(team)
                .startTime(LocalDateTime.now())
                .build();

        meetingRepository.save(meeting);

        return new StartMeetingResponse(meeting.getId(), meeting.getStartTime());
    }
}
