package com.example.blip_be.domain.meeting.presentation;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.presentation.dto.request.EndMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.request.JoinMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.request.LeaveMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.request.StartMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.response.EndMeetingResponse;
import com.example.blip_be.domain.meeting.presentation.dto.response.JoinMeetingResponse;
import com.example.blip_be.domain.meeting.presentation.dto.response.LeaveMeetingResponse;
import com.example.blip_be.domain.meeting.presentation.dto.response.StartMeetingResponse;
import com.example.blip_be.domain.meeting.service.EndMeetingService;
import com.example.blip_be.domain.meeting.service.JoinMeetingService;
import com.example.blip_be.domain.meeting.service.LeaveMeetingService;
import com.example.blip_be.domain.meeting.service.StartMeetingService;
import com.example.blip_be.global.security.auth.AuthDetails;
import com.example.blip_be.global.security.auth.AuthDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final StartMeetingService startMeetingService;
    private final JoinMeetingService joinMeetingService;
    private final LeaveMeetingService leaveMeetingService;
    private final EndMeetingService endMeetingService;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public StartMeetingResponse startMeeting(
            @RequestBody @Valid StartMeetingRequest request,
            @AuthenticationPrincipal AuthDetails authDetails) {

        Long leaderId = authDetails.getUser().getId();
        return startMeetingService.startMeeting(request, leaderId);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public JoinMeetingResponse joinMeeting(@RequestBody @Valid JoinMeetingRequest request) {
        return joinMeetingService.joinMeeting(request);
    }

    @PostMapping("/leave")
    @ResponseStatus(HttpStatus.OK)
    public LeaveMeetingResponse leaveMeeting(@RequestBody @Valid LeaveMeetingRequest request) {
        return leaveMeetingService.leaveMeeting(request);
    }

    @PostMapping("/end")
    @ResponseStatus(HttpStatus.OK)
    public EndMeetingResponse endMeeting(@RequestBody @Valid EndMeetingRequest request, @RequestPart MultipartFile file) {
        return endMeetingService.endMeeting(request, file);
    }
}
