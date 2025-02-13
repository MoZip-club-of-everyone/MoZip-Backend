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

    public static ApplicantException paperEvaluated(Applicant applicant) {
        return new ApplicantException(applicant, "이미 서류 평가된 Applicant입니다.");
    }

    public static ApplicantException interviewEvaluated(Applicant applicant) {
        return new ApplicantException(applicant, "이미 면접 평가된 Applicant입니다.");
    }

    public static ApplicantException paperNotEvaluated(Applicant applicant) {
        return new ApplicantException(applicant, "서류 평가가 완료되지 않은 Applicant입니다.");
    }

    public static ApplicantException interviewNotEvaluated(Applicant applicant) {
        return new ApplicantException(applicant, "면접 평가가 완료되지 않은 Applicant입니다.");
    }

    public static ApplicantException notRegistered(Applicant applicant) {
        return new ApplicantException(applicant, "등록되지 않은 Applicant입니다.");
    }
}