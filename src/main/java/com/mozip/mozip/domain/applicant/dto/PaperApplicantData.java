package com.mozip.mozip.domain.applicant.dto;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PaperApplicantData extends ApplicantData {
    private EvaluationStatus paperStatus;

    @Override
    public PaperApplicantData withStatus(Applicant applicant) {
        this.paperStatus = applicant.getPaperStatus();
        return this;
    }

    @Override
    public EvaluationStatus getStatus() {
        return this.paperStatus;
    }

    public static PaperApplicantData from(Applicant applicant, Double paperScore) {
        return PaperApplicantData.builder()
                .applicantId(applicant.getId())
                .applicationNumber(applicant.getApplicationNumber())
                .realname(applicant.getUser().getRealname())
                .appliedAt(applicant.getCreatedAt())
                .paperScore(paperScore)
                .email(applicant.getUser().getEmail())
                .phone(applicant.getUser().getPhone())
                .build()
                .withStatus(applicant);
    }
}