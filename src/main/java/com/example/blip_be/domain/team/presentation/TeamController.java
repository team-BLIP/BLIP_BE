package com.example.blip_be.domain.team.presentation;

import com.example.blip_be.domain.team.presentation.dto.request.CreateTeamRequest;
import com.example.blip_be.domain.team.presentation.dto.response.CreateTeamResponse;
import com.example.blip_be.domain.team.service.TeamCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamCreateService createTeamService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTeamResponse createTeam(@RequestBody @Valid CreateTeamRequest request) {
        return createTeamService.createTeam(request);
    }
}
