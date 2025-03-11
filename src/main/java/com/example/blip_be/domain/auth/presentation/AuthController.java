package com.example.blip_be.domain.auth.presentation;

import com.example.blip_be.domain.email.service.MailService;
import com.example.blip_be.domain.email.service.RedisEmailAuthentication;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MailService mailService;
    private final RedisEmailAuthentication redisEmailAuthentication;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendVerificationCode(@RequestParam @Valid String email) throws MessagingException {
        mailService.sendVerificationEmail(email);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyCode(@RequestParam String email, @RequestParam String code) {
        if (!mailService.verifyCode(email, code)) {
            throw new IllegalArgumentException("인증 코드가 올바르지 않습니다.");
        }
        redisEmailAuthentication.setEmailAuthenticationComplete(email);
    }
}
