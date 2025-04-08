package com.example.blip_be.domain.team.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.MeetingSummary;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.response.MeetingResponse;
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

        List<MeetingResponse> meetingResponses = meetingRepository
                .findAllByTeam(team)
                .stream()
                .map(this::convertToMeetingResponse)
                .toList();

        return new TeamDetailResponse(
                team.getId(),
                team.getTeamName(),
                team.getImageUrl(),
                meetingResponses
        );
    }

    private MeetingResponse convertToMeetingResponse(Meeting meeting) {
        String summaryContent = Optional.ofNullable(meeting.getMeetingSummary())
                .map(MeetingSummary::getSummary)
                .orElse(null);

        String feedbackContent = Optional.ofNullable(meeting.getFeedbacks())
                .filter(feedbacks -> !feedbacks.isEmpty())
                .map(feedbacks -> feedbacks.get(0).getFeedback())
                .orElse(null);

        return new MeetingResponse(summaryContent, feedbackContent);
    }
}