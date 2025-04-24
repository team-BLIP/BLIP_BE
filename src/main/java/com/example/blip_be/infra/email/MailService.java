package com.example.blip_be.infra.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Value("${MAIL_USERNAME}")
    private String mail;

    public void sendVerificationEmail(String email) throws MessagingException {
        String verificationCode = generateVerificationCode();
        saveVerificationCode(email, verificationCode, 3);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증 코드");
        message.setText("다음 코드를 입력하여 이메일 인증을 완료하세요 : " + verificationCode);
        message.setFrom(mail);

        mailSender.send(message);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public void saveVerificationCode(String email, String code, long minutes) {
        redisTemplate.delete(email);
        redisTemplate.opsForHash().put(email, "verification_code", code);
        redisTemplate.expire(email, Duration.ofMinutes(minutes));
    }

    public boolean verifyCode(String email, String inputCode) {
        String storedCode = (String) redisTemplate.opsForHash().get(email, "verification_code");
        boolean isValid = storedCode != null && storedCode.equals(inputCode);
        return isValid;
    }
}