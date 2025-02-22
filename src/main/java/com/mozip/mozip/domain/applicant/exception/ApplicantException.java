package com.mozip.mozip.domain.applicant.exception;

import com.mozip.mozip.domain.applicant.entity.Applicant;

public class ApplicantException extends RuntimeException {
    public ApplicantException(String applicantId, String message) {
        super("Id: " + applicantId + " - " + message);
    }

    public ApplicantException(Applicant applicant, String message) {
        super("Id: " + applicant.getId() + " - " + message);
    }

    public static ApplicantException notFound(String applicantId) {
        return new ApplicantException(applicantId, "Applicant를 찾을 수 없습니다.");
    }

    public static ApplicantException notEvaluated(Applicant applicant) {
        return new ApplicantException(applicant, "EvaluationStatus가 UNEVALUATED 상태입니다.");
    }

    public static ApplicantException notRegistered(Applicant applicant) {
        return new ApplicantException(applicant, "등록되지 않은 Applicant입니다.");
    }

    public static ApplicantException evaluationOnHold(Applicant applicant) {
        return new ApplicantException(applicant, "평가 제외된 지원자입니다.");
    }
}