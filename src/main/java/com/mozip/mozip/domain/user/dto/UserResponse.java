package com.mozip.mozip.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {
    private String userId;
    private String username;
    private String nickname;
    private String message;

    public static UserResponse from(User user, String message) {
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .message(message)
                .build();
    }
}
