package com.example.blip_be.infra.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String summary;
    private String feedback;
    private String time;
}