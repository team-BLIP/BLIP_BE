package com.example.blip_be.domain.auth.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;
}
