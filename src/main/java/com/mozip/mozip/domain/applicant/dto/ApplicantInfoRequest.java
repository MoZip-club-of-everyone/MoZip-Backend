package com.mozip.mozip.domain.applicant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicantInfoRequest {
    private String realname;
    private String phone;
    private String email;
} 