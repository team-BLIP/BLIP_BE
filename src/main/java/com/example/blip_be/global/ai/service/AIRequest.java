package com.example.blip_be.global.ai.service;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AIRequest {

    private String s3Url;
}