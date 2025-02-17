package com.example.blip_be.domain.user.service;

import com.example.blip_be.domain.auth.presentation.dto.TokenResponse;
import com.example.blip_be.domain.user.domain.User;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.PasswordMismatchedException;
import com.example.blip_be.domain.user.exception.UserNotFoundException;
import com.example.blip_be.domain.user.presentation.dto.request.LoginRequest;
import com.example.blip_be.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Transactional
    public TokenResponse login(LoginRequest request) {
        logger.info("로그인 요청 - 이메일: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("사용자 없음 - 이메일: {}", request.getEmail());
                    return UserNotFoundException.EXCEPTION;
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.error("비밀번호 불일치 - 이메일: {}", request.getEmail());
            throw PasswordMismatchedException.EXCEPTION;
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getAccountId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getAccountId());

        logger.info("로그인 성공 - 이메일: {}", request.getEmail());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
