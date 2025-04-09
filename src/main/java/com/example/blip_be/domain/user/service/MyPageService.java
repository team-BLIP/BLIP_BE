package com.example.blip_be.domain.user.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.TeamMember;
import com.example.blip_be.domain.team.domain.repository.TeamMemberRepository;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.presentation.dto.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final TeamMemberRepository teamMemberRepository;

    public MyPageResponse getMyPage(UserEntity user) {
        Team mainTeam = user.getMain();

        if (mainTeam == null) {
            TeamMember teamMember = teamMemberRepository.findByUserAndTeam(user, mainTeam)
                    .orElseThrow(()-> new NotFoundException("팀원 정보가 없습니다"));

            return MyPageResponse.builder()
                    .userId(user.getId())
                    .accountId(user.getAccountId())
                    .email(user.getEmail())
                    .imageUrl(user.getImageUrl())
                    .nickName(teamMember.getNickname())
                    .teamName(mainTeam.getTeamName())
                    .build();
        }
        else {
            return MyPageResponse.builder()
                    .userId(user.getId())
                    .accountId(user.getAccountId())
                    .email(user.getEmail())
                    .imageUrl(mainTeam.getImageUrl())
                    .build();
        }
    }
}