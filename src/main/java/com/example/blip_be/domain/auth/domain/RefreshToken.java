package com.example.blip_be.domain.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash(value = "refresh_token", timeToLive = 2592000)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    private String accountId;

    @Indexed
    private String token;
}
