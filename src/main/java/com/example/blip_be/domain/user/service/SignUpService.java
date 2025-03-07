package com.example.blip_be.domain.user.service;

import com.example.blip_be.domain.email.service.RedisEmailAuthentication;
import com.example.blip_be.domain.file.service.FileUploadService;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.UserExistException;
import com.example.blip_be.domain.user.presentation.dto.request.SignUpRequest;
import com.example.blip_be.global.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisEmailAuthentication redisEmailAuthentication;
    private final FileUploadService fileUploadService;

    @Transactional
    public void registerUser(SignUpRequest request) {

        validateUsername(request.getAccountId());
        validateMailAuthentication(request.getEmail());
        String voiceDataPath = fileUploadService.uploadVoiceFile(request.getVoiceData());

        UserEntity user = UserEntity.builder()
                .accountId(request.getAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .voiceDataPath(voiceDataPath)
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    private void validateUsername(String email) {
        if (userRepository.existsByEmail(email)) {
            throw UserExistException.EXCEPTION;
        }
    }

    private void validateMailAuthentication(String email) {
        String authStatus = redisEmailAuthentication.checkEmailAuthentication(email);
        if (authStatus == null || !"Y".equals(authStatus)) {
            throw new IllegalArgumentException("이메일이 인증되지 않았습니다.");
        }
    }
}
