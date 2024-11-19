package com.mozip.mozip.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String realname;
    private String email;
    private String phone;
}