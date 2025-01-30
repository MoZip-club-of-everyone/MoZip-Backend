package com.mozip.mozip.domain.applicant.exception;

public class ApplicantNotFoundException extends RuntimeException {

    public ApplicantNotFoundException(String applicantId) {
        super("Id: " + applicantId + " 의 Applicant를 찾을 수 없습니다.");
    }
}