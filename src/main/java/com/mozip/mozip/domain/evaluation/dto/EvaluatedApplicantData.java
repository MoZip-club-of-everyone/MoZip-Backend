package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.dto.ApplicantData;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EvaluatedApplicantData extends ApplicantData {
    private List<EvaluationData> evaluations;

    public static EvaluatedApplicantData from(Applicant applicant, Double paperScore, List<EvaluationData> evaluations) {
        return EvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .paperScore(paperScore)
                .email(applicant.getUser().getEmail())
                .status(applicant.getTotalStatus())
                .evaluations(evaluations)
                .build();
    }
}
