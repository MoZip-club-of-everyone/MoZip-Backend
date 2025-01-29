package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicantData {
    private String applicantId;
    private int applicationNumber;
    private String realname;
    private LocalDateTime appliedAt;
    private Double paperScore;
    private String email;
    private String phone;
    private EvaluationStatus status;

    public static ApplicantData from(Applicant applicant, Double paperScore) {
        return ApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .paperScore(paperScore)
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .status(applicant.getTotalStatus())
                .build();
    }
}