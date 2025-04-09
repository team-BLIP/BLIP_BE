package com.example.blip_be.domain.user.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageResponse {

    private Long userId;
    private String accountId;
    private String imageUrl;
    private String email;
    private String nickName;
    private String teamName;
}
