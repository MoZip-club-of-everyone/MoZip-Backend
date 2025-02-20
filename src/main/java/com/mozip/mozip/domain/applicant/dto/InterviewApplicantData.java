package com.mozip.mozip.domain.applicant.dto;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class InterviewApplicantData extends ApplicantData {
    private Double paperScoreAverage;
    private Double interviewScoreAverage;
    private Double interviewScoreStandardDeviation;
    private EvaluationStatus interviewStatus;

    @Override
    public InterviewApplicantData withStatus(Applicant applicant) {
        this.interviewStatus = applicant.getInterviewStatus();
        return this;
    }

    @Override
    @JsonIgnore
    public EvaluationStatus getStatus() {
        return this.interviewStatus;
    }

    public static InterviewApplicantData from(Applicant applicant) {
        return InterviewApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .paperScoreAverage(applicant.getPaperScoreAverage())
                .interviewScoreAverage(applicant.getInterviewScoreAverage())
                .interviewScoreStandardDeviation(applicant.getInterviewStandardDeviation())
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .build()
                .withStatus(applicant);
    }
}