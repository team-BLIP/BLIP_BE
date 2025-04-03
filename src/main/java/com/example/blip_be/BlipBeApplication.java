package com.example.blip_be;

import com.example.blip_be.global.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class BlipBeApplication {
    /**
     * Entry point for the Spring Boot application.
     * <p>
     * Bootstraps the application by invoking {@code SpringApplication.run} with the main application class
     * and any supplied command-line arguments.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(BlipBeApplication.class, args);
    }
}
