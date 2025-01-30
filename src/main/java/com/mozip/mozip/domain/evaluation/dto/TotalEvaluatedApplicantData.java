package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.dto.ApplicantData;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TotalEvaluatedApplicantData extends ApplicantData {
    private List<EvaluationData> evaluations;
    private EvaluationStatus status;

    @Override
    public TotalEvaluatedApplicantData withStatus(Applicant applicant) {
        this.status = applicant.getTotalStatus();
        return this;
    }

    @Override
    public EvaluationStatus getStatus() {
        return this.status;
    }

    public static TotalEvaluatedApplicantData from(Applicant applicant, Double paperScore, List<EvaluationData> evaluations) {
        return TotalEvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .paperScore(paperScore)
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .status(applicant.getTotalStatus())
                .evaluations(evaluations)
                .build();
    }
}
