package com.example.blip_be.domain.team.presentation;

import com.example.blip_be.domain.team.presentation.dto.request.CreateTeamRequest;
import com.example.blip_be.domain.team.presentation.dto.request.TeamJoinRequest;
import com.example.blip_be.domain.team.presentation.dto.request.UpdateTeamSettingRequest;
import com.example.blip_be.domain.team.presentation.dto.response.CreateTeamResponse;
import com.example.blip_be.domain.team.presentation.dto.response.TeamDetailResponse;
import com.example.blip_be.domain.team.presentation.dto.response.TeamListResponse;
import com.example.blip_be.domain.team.service.*;
import com.example.blip_be.global.security.auth.AuthDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamCreateService createTeamService;
    private final TeamJoinService teamJoinService;
    private final UpdateTeamSettingService updateTeamSettingService;
    private final DeleteTeamService deleteTeamService;
    private final TeamQueryService teamQueryService;
    private final TeamDetailService teamDetailService;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TeamListResponse> getMyTeams(@AuthenticationPrincipal AuthDetails authDetails) {
        return teamQueryService.getMyTeams(authDetails.getUser().getId());
    }

    @GetMapping("/{team-id}")
    @ResponseStatus(HttpStatus.OK)
    public TeamDetailResponse getTeamDetail(@PathVariable ("team-id")Long teamId) {
        return teamDetailService.getTeamDetail(teamId);
    }
}
