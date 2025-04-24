package com.example.blip_be.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String parseToken = jwtTokenProvider.resolveToken(request); //순수한 ㅌㅋ

        if (parseToken != null) {
            Authentication authentication = jwtTokenProvider.authentication(parseToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("컨텍스트에 인증 설정됨: {}", authentication.getName());
        }
        filterChain.doFilter(request, response);
    }
}
