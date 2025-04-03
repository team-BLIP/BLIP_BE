package com.example.blip_be.global.security;

import com.example.blip_be.global.enums.Permission;
import com.example.blip_be.global.error.ExceptionFilter;
import com.example.blip_be.global.security.jwt.JwtTokenFilter;
import com.example.blip_be.global.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private final String[] PERMIT_ALL_URL = {
            "/auth/**",
            "/users/login",
            "/users/signup"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the application's security filter chain.
     *
     * <p>This method sets up Spring Security to operate with stateless session management by disabling CSRF protection
     * and form-based login, while enabling CORS using default settings. It permits requests to public endpoints specified
     * in the PERMIT_ALL_URL array and requires authentication for specific HTTP methods on "/teams/**", "/meetings/**",
     * and "/invite/**" endpoints. Additionally, it integrates a JWT token validation filter and an exception handling filter
     * into the filter chain.</p>
     *
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during the configuration of the security filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                .csrf(CsrfConfigurer::disable)
                 .cors(Customizer.withDefaults())
                 .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(PERMIT_ALL_URL).permitAll()
                                .requestMatchers(HttpMethod.GET, "/teams/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/teams/**").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/teams/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/teams/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/meetings/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/invite/**").authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionFilter(objectMapper), JwtTokenFilter.class);

        return http.build();
    }
}