package com.example.blip_be.domain.meeting.presentation;

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public StartMeetingResponse startMeeting(@RequestBody @Valid StartMeetingRequest request) {
        return startMeetingService.startMeeting(request);
    }

    @PostMapping("/join/{meeting-id}")
    @ResponseStatus(HttpStatus.OK)
    public JoinMeetingResponse joinMeeting(@PathVariable ("meeting-id") Long meetingId) {
        return joinMeetingService.joinMeeting(meetingId);
    }

    @PostMapping("/leave/{meeting-id}")
    @ResponseStatus(HttpStatus.OK)
    public LeaveMeetingResponse leaveMeeting(@PathVariable ("meeting-id") Long meetingId) {
        return leaveMeetingService.leaveMeeting(meetingId);
    }

    @PostMapping("/end/{meeting-id}")
    @ResponseStatus(HttpStatus.OK)
    public EndMeetingResponse endMeeting(@PathVariable ("meeting-id") Long meetingId) {
        return endMeetingService.endMeeting(meetingId);
    }
}