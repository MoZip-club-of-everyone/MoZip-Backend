package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.ApplicationStatus;
import com.mozip.mozip.domain.club.entity.Mozip;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicantListResponse {
    private String mozipId;
    private int totalCnt;
    private int passedCnt;
    private int failedCnt;
    private List<ApplicationDto> applicants;

    public static ApplicantListResponse from(List<Applicant> applicants, Mozip mozip) {
        long totalCount = applicants.size();
        long passedCount = applicants.stream()
                .filter(applicant -> applicant.getTotalStatus() == ApplicationStatus.PASSED)
                .count();
        long failedCount = applicants.stream()
                .filter(applicant -> applicant.getTotalStatus() == ApplicationStatus.FAILED)
                .count();

        List<ApplicationDto> applicantDtos = applicants.stream()
                .map(ApplicationDto::from)
                .collect(Collectors.toList());

        return ApplicantListResponse.builder()
                .mozipId(mozip.getId())
                .totalCnt((int) totalCount)
                .passedCnt((int) passedCount)
                .failedCnt((int) failedCount)
                .applicants(applicantDtos)
                .build();
    }
}