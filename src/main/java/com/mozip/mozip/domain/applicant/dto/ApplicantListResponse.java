package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.enums.ApplicationStatus;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicantListResponse {
    private String mozipId;
    private int totalCnt;
    private int passedCnt;
    private int failedCnt;
    private List<ApplicationDto> applicants;

    public static ApplicantListResponse from(List<ApplicationDto> applicants, Mozip mozip) {
        int totalCount = applicants.size();
        int passedCount = (int) applicants.stream()
                .filter(applicant -> applicant.getStatus() == ApplicationStatus.PASSED)
                .count();
        int failedCount = (int) applicants.stream()
                .filter(applicant -> applicant.getStatus() == ApplicationStatus.FAILED)
                .count();

        return ApplicantListResponse.builder()
                .mozipId(mozip.getId())
                .totalCnt(totalCount)
                .passedCnt(passedCount)
                .failedCnt(failedCount)
                .applicants(applicants)
                .build();
    }
}