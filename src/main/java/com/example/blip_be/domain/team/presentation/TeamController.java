package com.example.blip_be.domain.team.presentation;

import com.example.blip_be.domain.team.presentation.dto.request.CreateTeamRequest;
import com.example.blip_be.domain.team.presentation.dto.request.TeamJoinRequest;
import com.example.blip_be.domain.team.presentation.dto.response.CreateTeamResponse;
import com.example.blip_be.domain.team.service.TeamCreateService;
import com.example.blip_be.domain.team.service.TeamJoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamCreateService createTeamService;
    private final TeamJoinService teamJoinService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTeamResponse createTeam(@RequestBody @Valid CreateTeamRequest request) {
        return createTeamService.createTeam(request);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinTeam(@RequestBody @Valid  TeamJoinRequest request) {
        teamJoinService.joinTeam(request);
    }
}
