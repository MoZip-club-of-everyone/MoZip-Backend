package com.mozip.mozip.domain.applicant.dto;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApplicationDto {
    private String applicantId;
    private int applicationNumber;
    private String realname;
    private LocalDateTime appliedAt;
    private Integer paperScore;
    private String email;
    private String phone;
    private ApplicationStatus status;

    public static ApplicationDto from(Applicant applicant, Integer paperScore) {
        return ApplicationDto.builder()
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