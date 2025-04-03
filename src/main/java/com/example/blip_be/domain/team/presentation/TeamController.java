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

    /**
     * Creates a new team from the provided request data.
     *
     * <p>This method processes a validated team creation request and delegates the operation to the corresponding service,
     * returning a {@code CreateTeamResponse} that details the newly created team. The response is issued with an HTTP status of 201 (Created).
     *
     * @param request the team creation request containing necessary team information
     * @return a response object containing information about the created team
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTeamResponse createTeam(@RequestBody @Valid CreateTeamRequest request) {
        return createTeamService.createTeam(request);
    }

    /**
     * Processes a team join request.
     * 
     * <p>Validates the provided join request and delegates the team joining operation to the underlying service.
     * Responds with HTTP status 200 (OK) upon successful processing.</p>
     *
     * @param request the join request containing the required team joining details
     */
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinTeam(@RequestBody @Valid  TeamJoinRequest request) {
        teamJoinService.joinTeam(request);
    }

    /**
     * Updates the settings for the specified team.
     * <p>
     * Processes a PATCH request to update the team's settings using the provided update request data.
     * Responds with HTTP 200 (OK) upon a successful update.
     * </p>
     *
     * @param teamId  the unique identifier of the team to update
     * @param request the update request containing the new team settings (validated)
     */
    @PatchMapping("/{team-id}/setting")
    @ResponseStatus(HttpStatus.OK)
    public void UpdateTeam(@PathVariable("team-id") Long teamId, @RequestBody @Valid UpdateTeamSettingRequest request) {
        updateTeamSettingService.updateTeam(teamId, request);
    }

    /**
     * Deletes the team identified by the given team ID.
     *
     * <p>This endpoint handles DELETE requests to remove the specified team, returning an HTTP 200 status upon successful deletion.</p>
     *
     * @param teamId the unique identifier of the team to be deleted
     */
    @DeleteMapping("/{team-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable("team-id") Long teamId) {
        deleteTeamService.delete(teamId);
    }
}
