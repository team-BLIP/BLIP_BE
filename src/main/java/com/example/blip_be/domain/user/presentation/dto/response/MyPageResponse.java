package com.example.blip_be.domain.user.presentation.dto.response;

import com.example.blip_be.domain.meeting.presentation.dto.response.MeetingFeedbackResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class MyPageResponse {

    private Long userId;
    private String accountId;
    private String imageUrl;
    private String email;
    private String nickName;
    private List<MeetingFeedbackResponse> feedbackList;
}