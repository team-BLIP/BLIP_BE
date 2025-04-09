package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.MeetingSummary;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.response.MeetingSummaryResponse;
import com.example.blip_be.domain.team.TeamNotFoundException;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.response.TeamDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamDetailService {

    private final TeamRepository teamRepository;
    private final MeetingRepository meetingRepository;

    public TeamDetailResponse getTeamDetail(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> TeamNotFoundException.EXCEPTION);

        List<MeetingSummaryResponse> meetingSummaries = meetingRepository
                .findAllByTeam(team)
                .stream()
                .map(this::convertToMeetingSummaryResponse)
                .toList();

        return new TeamDetailResponse(
                team.getId(),
                team.getTeamName(),
                team.getImageUrl(),
                meetingSummaries
        );
    }

    private MeetingSummaryResponse convertToMeetingSummaryResponse(Meeting meeting) {
        String summaryContent = Optional.ofNullable(meeting.getMeetingSummary())
                .map(MeetingSummary::getSummary)
                .orElse(null);

        return new MeetingSummaryResponse(meeting.getId(), summaryContent);
    }
}