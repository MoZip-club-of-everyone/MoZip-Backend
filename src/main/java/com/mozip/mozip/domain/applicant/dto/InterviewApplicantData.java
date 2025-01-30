package com.mozip.mozip.domain.applicant.dto;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class InterviewApplicantData extends ApplicantData {
    private EvaluationStatus interviewStatus;

    @Override
    public InterviewApplicantData withStatus(Applicant applicant) {
        this.interviewStatus = applicant.getInterviewStatus();
        return this;
    }

    @Override
    public EvaluationStatus getStatus() {
        return this.interviewStatus;
    }

    public static InterviewApplicantData from(Applicant applicant, Double interviewScore) {
        return InterviewApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .paperScore(interviewScore)
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .build()
                .withStatus(applicant);
    }
}