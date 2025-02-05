package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.dto.PaperApplicantData;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperEvaluatedApplicantData extends PaperApplicantData {
    private List<PaperEvaluationData> evaluations;

    public static PaperEvaluatedApplicantData from(Applicant applicant, List<PaperEvaluationData> evaluations) {
        return PaperEvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .evaluations(evaluations)
                .paperScoreAverage(applicant.getPaperScoreAverage())
                .paperScoreStandardDeviation(applicant.getPaperScoreStandardDeviation())
                .paperStatus(applicant.getPaperStatus())
                .build();

    }
} 