package com.example.blip_be.domain.meeting.service;

import com.example.blip_be.domain.file.service.FileUploadService;
import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.MeetingFeedback;
import com.example.blip_be.domain.meeting.domain.MeetingSummary;
import com.example.blip_be.domain.meeting.domain.repository.MeetingFeedbackRepository;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.domain.repository.MeetingSummaryRepository;
import com.example.blip_be.domain.meeting.presentation.dto.request.EndMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.response.EndMeetingResponse;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.global.ai.service.Result;
import com.example.blip_be.global.ai.service.WebClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EndMeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingFeedbackRepository meetingFeedbackRepository;
    private final MeetingSummaryRepository meetingSummaryRepository;
    private final FileUploadService fileUploadService;
    private final WebClientService webClientService;

    @Transactional
    public EndMeetingResponse endMeeting(EndMeetingRequest request, MultipartFile file) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회의"));

        Team team = meeting.getTeam();

        if (!team.getLeader().getId().equals(request.getLeaderId())) {
            throw new IllegalArgumentException("회의를 종료할 권한이 없습니다.");
        }

        String fileUrl = fileUploadService.uploadAudioFile(file);

        meeting = Meeting.builder()
                .fileUrl(fileUrl)
                .endTime(LocalDateTime.now())
                .build();

        meetingRepository.save(meeting);

        analyzeAndSaveFeedbackAndSummary(meeting);

        return EndMeetingResponse.builder()
                .meetingId(request.getMeetingId())
                .endTime(LocalDateTime.now())
                .fileUrl(fileUrl)
                .build();
    }

    private void analyzeAndSaveFeedbackAndSummary(Meeting meeting) {
        String fileUrl = meeting.getFileUrl();
        String meetingId = meeting.getId().toString();

        Result analysisResult = webClientService.analyzeMeeting(meetingId, fileUrl)
                .block(); 

        meetingFeedbackRepository.save(new MeetingFeedback(meeting, analysisResult.getFeedback()));
        meetingSummaryRepository.save(new MeetingSummary(meeting, analysisResult.getSummary()));
    }

}
