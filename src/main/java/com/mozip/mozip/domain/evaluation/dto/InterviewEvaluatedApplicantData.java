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
public class InterviewEvaluatedApplicantData extends ApplicantData {
    private List<InterviewEvaluationData> evaluations;
    private Double interviewScore;
    private EvaluationStatus status;

    @Override
    public InterviewEvaluatedApplicantData withStatus(Applicant applicant) {
        this.status = applicant.getInterviewStatus();
        return this;
    }

    @Override
    public EvaluationStatus getStatus() {
        return this.status;
    }

    public static InterviewEvaluatedApplicantData from(Applicant applicant, Double interviewScore, List<InterviewEvaluationData> evaluations) {
        return InterviewEvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .evaluations(evaluations)
                .interviewScore(interviewScore)
                .status(applicant.getInterviewStatus())
                .build();
    }
} 