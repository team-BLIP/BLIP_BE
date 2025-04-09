package com.example.blip_be.domain.user.service;

import com.example.blip_be.domain.meeting.domain.MeetingFeedback;
import com.example.blip_be.domain.meeting.domain.repository.MeetingFeedbackRepository;
import com.example.blip_be.domain.meeting.presentation.dto.response.MeetingFeedbackResponse;
import com.example.blip_be.domain.team.domain.TeamMember;
import com.example.blip_be.domain.team.domain.repository.TeamMemberRepository;
import com.example.blip_be.domain.team.exception.TeamMemberNotFoundException;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.UserNotFoundException;
import com.example.blip_be.domain.user.presentation.dto.response.MyPageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MeetingFeedbackRepository meetingFeedbackRepository;

    @Transactional
    public MyPageResponse getMyPage(Long userId, Long teamId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        TeamMember teamMember = teamMemberRepository.findByUserIdAndTeamId(userId, teamId)
                .orElseThrow(() -> TeamMemberNotFoundException.EXCEPTION);

        List<MeetingFeedbackResponse> feedbackResponses = meetingFeedbackRepository.findAllByMeeting_Team_Id(teamId)
                .stream()
                .map(this::convertToFeedbackResponse)
                .collect(Collectors.toList());

        return MyPageResponse.builder()
                .userId(userId)
                .accountId(user.getAccountId())
                .imageUrl(user.getImageUrl())
                .email(user.getEmail())
                .nickName(teamMember.getNickname())
                .feedbackList(feedbackResponses)
                .build();
    }

    private MeetingFeedbackResponse convertToFeedbackResponse(MeetingFeedback feedback) {
        return new MeetingFeedbackResponse(
                feedback.getMeeting().getId(),
                feedback.getFeedback()
        );
    }
}
