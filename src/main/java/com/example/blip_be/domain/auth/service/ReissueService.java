package com.example.blip_be.domain.auth.service;

import com.example.blip_be.domain.auth.domain.RefreshToken;
import com.example.blip_be.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.blip_be.domain.auth.exception.RefreshTokenNotFoundException;
import com.example.blip_be.domain.auth.presentation.dto.TokenResponse;
import com.example.blip_be.global.exception.InvalidTokenException;
import com.example.blip_be.global.security.jwt.JwtProperties;
import com.example.blip_be.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Transactional
    public TokenResponse reissue(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization");
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw InvalidTokenException.EXCEPTION;
        }

        String parseToken = jwtTokenProvider.parseToken(refreshToken.substring(7));
        if (parseToken == null) {
            throw InvalidTokenException.EXCEPTION;
        }

        RefreshToken redisRefreshToken = refreshTokenRepository.findByToken(parseToken)
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        String accountId = redisRefreshToken.getAccountId();
        refreshTokenRepository.delete(redisRefreshToken);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(accountId))
                .accessExpiredAt(now.plusSeconds(jwtProperties.getAccessExp()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(accountId))
                .refreshExpiredAt(now.plusSeconds(jwtProperties.getRefreshExp()))
                .build();
    }
}
