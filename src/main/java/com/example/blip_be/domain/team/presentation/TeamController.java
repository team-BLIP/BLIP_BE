package com.example.blip_be.domain.team.presentation;

import com.example.blip_be.domain.team.presentation.dto.request.CreateTeamRequest;
import com.example.blip_be.domain.team.presentation.dto.request.TeamJoinRequest;
import com.example.blip_be.domain.team.presentation.dto.request.UpdateTeamSettingRequest;
import com.example.blip_be.domain.team.presentation.dto.response.CreateTeamResponse;
import com.example.blip_be.domain.team.service.DeleteTeamService;
import com.example.blip_be.domain.team.service.TeamCreateService;
import com.example.blip_be.domain.team.service.TeamJoinService;
import com.example.blip_be.domain.team.service.UpdateTeamSettingService;
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
    private final UpdateTeamSettingService updateTeamSettingService;
    private final DeleteTeamService deleteTeamService;

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

    @PatchMapping("/{team-id}/setting")
    @ResponseStatus(HttpStatus.OK)
    public void UpdateTeam(@PathVariable("team-id") Long teamId, @RequestBody @Valid UpdateTeamSettingRequest request) {
        updateTeamSettingService.updateTeam(teamId, request);
    }

    @DeleteMapping("/{team-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable("team-id") Long teamId) {
        deleteTeamService.delete(teamId);
    }
}
