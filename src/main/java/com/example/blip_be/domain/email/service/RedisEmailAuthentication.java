package com.example.blip_be.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisEmailAuthentication {

    private final StringRedisTemplate redisTemplate;

    private static final String AUTH_KEY = "auth";
    private static final String CODE_KEY = "code";
    private static final String AUTH_YES = "Y";
    private static final String AUTH_NO = "N";

    public String checkEmailAuthentication(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, AUTH_KEY);
    }

    public String getEmailAuthenticationCode(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, CODE_KEY);
    }

    public void setEmailAuthenticationExpire(String email, String code, long duration) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(email, CODE_KEY, code);
        hashOperations.put(email, AUTH_KEY, AUTH_NO);
        redisTemplate.expire(email, Duration.ofMinutes(duration));
    }

    public void setEmailAuthenticationComplete(String email) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(email, AUTH_KEY, AUTH_YES);
    }

    public void deleteEmailAuthenticationHistory(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, CODE_KEY);
        hashOperations.delete(key, AUTH_KEY);
    }
}