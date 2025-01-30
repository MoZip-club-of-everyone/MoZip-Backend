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
    private EvaluationStatus interviewStatus;
    private List<InterviewEvaluationData> evaluations;

    @Override
    public InterviewEvaluatedApplicantData withStatus(Applicant applicant) {
        this.interviewStatus = applicant.getInterviewStatus();
        return this;
    }

    @Override
    public EvaluationStatus getStatus() {
        return this.interviewStatus;
    }

    public static InterviewEvaluatedApplicantData from(Applicant applicant, Double interviewScore, List<InterviewEvaluationData> evaluations) {
        return InterviewEvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .paperScore(interviewScore)
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .interviewStatus(applicant.getInterviewStatus())
                .evaluations(evaluations)
                .build();
    }
} 