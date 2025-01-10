package com.mozip.mozip.domain.applicant.dto;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationDto {
    private String applicantId;
    private String username;
    private String realname;
    private int applicationNumber;
    private ApplicationStatus paperStatus;
    private ApplicationStatus interviewStatus;
    private ApplicationStatus totalStatus;

    public static ApplicationDto from(Applicant applicant) {
        return ApplicationDto.builder()
                .applicantId(applicant.getId())
                .username(applicant.getUser().getUsername())
                .realname(applicant.getUser().getRealname())
                .applicationNumber(applicant.getApplicationNumber())
                .paperStatus(applicant.getPaperStatus())
                .interviewStatus(applicant.getInterviewStatus())
                .totalStatus(applicant.getTotalStatus())
                .build();
    }
}