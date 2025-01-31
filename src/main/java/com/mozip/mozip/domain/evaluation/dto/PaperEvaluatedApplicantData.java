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
public class PaperEvaluatedApplicantData extends ApplicantData {
    private List<PaperEvaluationData> evaluations;
    private Double paperScore;
    private EvaluationStatus status;


    @Override
    public PaperEvaluatedApplicantData withStatus(Applicant applicant) {
        this.status = applicant.getPaperStatus();
        return this;
    }

    @Override
    public EvaluationStatus getStatus() {
        return this.status;
    }

    public static PaperEvaluatedApplicantData from(Applicant applicant, Double paperScore, List<PaperEvaluationData> evaluations) {
        return PaperEvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .evaluations(evaluations)
                .paperScore(paperScore)
                .status(applicant.getPaperStatus())
                .build();
    }
} 