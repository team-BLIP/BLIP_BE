package com.example.blip_be.domain.meeting.service;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.response.EndMeetingResponse;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.infra.file.PresignedUrlService;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.facade.UserFacade;
import com.example.blip_be.infra.ai.Result;
import com.example.blip_be.infra.ai.WebClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EndMeetingService {

    private final MeetingRepository meetingRepository;
    private final WebClientService webClientService;
    private final PresignedUrlService presignedUrlService;
    private final UserFacade userFacade;

    @Value("${cloud.aws.s3.audio-path}")
    private String audioPath;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public EndMeetingResponse endMeeting(Long meetingId) {

        UserEntity user = userFacade.getCurrentUser();

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회의"));

        Team team = meeting.getTeam();
        if (!team.getLeader().getId().equals(user.getId())) {
            throw new IllegalArgumentException("회의를 종료할 권한이 없습니다.");
        }

        String key = audioPath + "meeting_" + meetingId + ".mp3";

        URL presignedPutUrl = presignedUrlService.generatePresignedUrl(key);

        URL presignedGetUrl = presignedUrlService.generatePresignedDownloadUrl(key);

        String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
        LocalDateTime endTime = LocalDateTime.now();
        meeting.endMeeting(fileUrl, endTime);

        Result result = webClientService.analyzeMeeting(presignedGetUrl.toString()).block();
        meeting.applyAnalysisResult(result.getSummary(), result.getFeedback());

        meetingRepository.save(meeting);

        return EndMeetingResponse.builder()
                .endTime(endTime)
                .fileUrl(fileUrl)
                .presignedUrl(presignedPutUrl.toString())
                .build();
    }
}