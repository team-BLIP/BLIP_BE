package com.example.blip_be.domain.user.service;

import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.exception.PasswordMismatchedException;
import com.example.blip_be.domain.user.facade.UserFacade;
import com.example.blip_be.domain.user.presentation.dto.request.ChangePasswordRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void changePassword(ChangePasswordRequest request) {

        UserEntity user = userFacade.getCurrentUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw PasswordMismatchedException.EXCEPTION;
        }
        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }
}
