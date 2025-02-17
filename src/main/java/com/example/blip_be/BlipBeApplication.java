package com.example.blip_be;

import com.example.blip_be.global.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class BlipBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlipBeApplication.class, args);
    }

}
