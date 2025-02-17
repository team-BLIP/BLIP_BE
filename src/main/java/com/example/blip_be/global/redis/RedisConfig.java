package com.example.blip_be.global.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private final long sessionTime = 5000;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        logger.info("📌 Redis 연결 시도 - Host: {}, Port: {}", redisHost, redisPort);

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(sessionTime))
                .build();

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config, clientConfig);
        connectionFactory.afterPropertiesSet();

        logger.info("✅ Redis 연결 성공 - Host: {}, Port: {}", redisHost, redisPort);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Map<String, String>> redisEmailAuthenticationTemplate() {
        logger.info("📌 RedisTemplate 생성...");

        RedisTemplate<String, Map<String, String>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        logger.info("✅ RedisTemplate 설정 완료");
        return redisTemplate;
    }
}