package com.example.blip_be.global.security.jwt;

import com.example.blip_be.domain.auth.domain.RefreshToken;
import com.example.blip_be.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.blip_be.global.exception.ExpiredTokenException;
import com.example.blip_be.global.exception.InvalidTokenException;
import com.example.blip_be.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(String accountId) {
        String token = generateToken(accountId, "access", jwtProperties.getAccessExp());
        logger.info("계정 ID: {}에 대해 액세스 토큰 생성됨: {}", accountId, token);
        return token;
    }

    public String generateRefreshToken(String accountId) {
        String refreshToken = generateToken(accountId, "refresh", jwtProperties.getRefreshExp());
        logger.info("계정 ID: {}에 대해 리프레시 토큰 생성됨: {}", accountId, refreshToken);

        refreshTokenRepository.save(RefreshToken.builder()
                .accountId(accountId)
                .token(refreshToken)
                .build());
        return refreshToken;
    }

    public String generateToken(String accountId, String type, Long exp) {
        logger.debug("계정 ID: {}에 대해 토큰 생성 중. 유형: {}, 만료 시간: {}초", accountId, type, exp);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .setSubject(accountId)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getPrefix())) {
            String token = bearerToken.replace(jwtProperties.getPrefix(), "");
            logger.info("해당 토큰 해석됨: {}", token);
            return token;
        }
        logger.warn("요청 헤더에서 베어러 토큰을 찾을 수 없거나 잘못된 형식임");
        return null;
    }

    public Authentication authentication(String token) {
        String subject = getTokenSubject(token);
        logger.info("계정 ID: {}로 인증 중", subject);

        UserDetails userDetails = authDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("JWT token is invalid", e);
        }
    }

    private Claims getTokenBody(String token) {
        try {
            System.out.println("토큰: " + token);
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredTokenException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private String getTokenSubject(String token) {
        try {
            String subject = getTokenBody(token).getSubject();
            logger.debug("토큰에서 추출된 계정 ID: {}", subject);
            return subject;
        } catch (Exception e) {
            logger.error("토큰에서 계정 ID 추출 오류: {}", e.getMessage());
            throw InvalidTokenException.EXCEPTION;
        }
    }
}