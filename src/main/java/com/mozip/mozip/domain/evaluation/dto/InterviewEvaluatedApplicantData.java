package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.dto.InterviewApplicantData;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterviewEvaluatedApplicantData extends InterviewApplicantData {
    private List<InterviewEvaluationData> evaluations;

    public static InterviewEvaluatedApplicantData from(Applicant applicant, List<InterviewEvaluationData> evaluations) {
        return InterviewEvaluatedApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .evaluations(evaluations)
                .interviewScoreAverage(applicant.getInterviewScoreAverage())
                .interviewScoreStandardDeviation(applicant.getInterviewStandardDeviation())
                .interviewStatus(applicant.getInterviewStatus())
                .build();


    }
} 