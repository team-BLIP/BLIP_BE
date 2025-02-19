package com.example.blip_be.global.security.jwt;

import com.example.blip_be.domain.auth.domain.RefreshToken;
import com.example.blip_be.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.blip_be.global.exception.ExpiredTokenException;
import com.example.blip_be.global.exception.InvalidTokenException;
import com.example.blip_be.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private SecretKeySpec secretKeySpec;

    @PostConstruct
    public void initSecretKeySpec() {
        this.secretKeySpec = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessToken(String accountId) {
        String token = generateToken(accountId, "access", jwtProperties.getAccessExp());
        return token;
    }

    public String generateRefreshToken(String accountId) {
        String refreshToken = generateToken(accountId, "refresh", jwtProperties.getRefreshExp());
        refreshTokenRepository.save(RefreshToken.builder()
                .accountId(accountId)
                .token(refreshToken)
                .ttl(jwtProperties.getRefreshExp())
                .build());
        return refreshToken;
    }

    public String generateToken(String accountId, String type, Long exp) {
        logger.debug("계정 ID: {}에 대해 토큰 생성 중. 유형: {}, 만료 시간: {}초", accountId, type, exp);
        return Jwts.builder()
                .signWith(secretKeySpec)
                .setSubject(accountId)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        return parseToken(bearerToken);
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String parseToken(String bearerToken) {

        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getPrefix())) {
            return bearerToken.replace(jwtProperties.getPrefix(), "").trim();
        }
        return null;
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKeySpec)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private String getTokenSubject(String token) {
        return getTokenBody(token).getSubject();
    }
}