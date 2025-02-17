package com.example.blip_be.domain.user.facade;

import com.example.blip_be.domain.user.domain.User;
import com.example.blip_be.domain.user.domain.repository.UserRepository;
import com.example.blip_be.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByAccountId(accountId)
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);
    }

    public Boolean checkUserExist(String accountId) {
        return userRepository.existsByAccountId(accountId);
    }
}
