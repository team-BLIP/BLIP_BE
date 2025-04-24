package com.example.blip_be.infra.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AIRequest {
    @JsonProperty("s3_url")
    private String s3Url;
}