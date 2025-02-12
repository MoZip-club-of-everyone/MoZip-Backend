package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicantInfoResponse {
    private final String applicantId;
    private final String realname;
    private final String phone;
    private final String email;

    public static ApplicantInfoResponse from(Applicant applicant) {
        return ApplicantInfoResponse.builder()
                .applicantId(applicant.getId())
                .realname(applicant.getUser().getRealname())
                .phone(applicant.getUser().getPhone())
                .email(applicant.getUser().getEmail())
                .build();
    }
} 