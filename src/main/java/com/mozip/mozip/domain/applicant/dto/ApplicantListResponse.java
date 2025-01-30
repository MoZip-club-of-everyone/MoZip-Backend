package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicantListResponse<T extends ApplicantData> {
    private int totalCnt;
    private int passedCnt;
    private int failedCnt;
    private List<T> applicants;

    public static <T extends ApplicantData> ApplicantListResponse<T> from(List<T> applicantDataList) {
        int totalCount = applicantDataList.size();
        int passedCount = (int) applicantDataList.stream()
                .filter(applicant -> applicant.getStatus() == EvaluationStatus.PASSED)
                .count();
        int failedCount = (int) applicantDataList.stream()
                .filter(applicant -> applicant.getStatus() == EvaluationStatus.FAILED)
                .count();

        return ApplicantListResponse.<T>builder()
                .totalCnt(totalCount)
                .passedCnt(passedCount)
                .failedCnt(failedCount)
                .applicants(applicantDataList)
                .build();
    }
}