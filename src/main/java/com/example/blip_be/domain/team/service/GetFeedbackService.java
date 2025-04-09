package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.meeting.domain.repository.MeetingFeedbackRepository;
import com.example.blip_be.domain.meeting.presentation.dto.response.MeetingFeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetFeedbackService {

    private final MeetingFeedbackRepository meetingFeedbackRepository;

    public List<MeetingFeedbackResponse> getFeedback(Long teamId) {
        return meetingFeedbackRepository.findAllByMeeting_Team_Id(teamId)
                .stream()
                .map(meetingFeedback -> new MeetingFeedbackResponse(
                        meetingFeedback.getMeeting().getId(),
                        meetingFeedback.getFeedback()
                ))
                .collect(Collectors.toList());
    }
}
