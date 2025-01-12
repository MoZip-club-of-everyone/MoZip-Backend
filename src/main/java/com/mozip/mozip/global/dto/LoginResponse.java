package com.mozip.mozip.global.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse {
    private String userId;
    private String message;
    private String accessToken;

    public static LoginResponse from(String userId,String message, String accessToken) {
        return LoginResponse.builder()
                .userId(userId)
                .message(message)
                .accessToken(accessToken)
                .build();
    }
}