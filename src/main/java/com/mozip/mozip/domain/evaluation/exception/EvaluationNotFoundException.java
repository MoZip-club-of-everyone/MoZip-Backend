package com.mozip.mozip.domain.evaluation.exception;

public class EvaluationNotFoundException extends RuntimeException {
    public EvaluationNotFoundException(String evaluatorId, String applicantId) {
        super("Evaluator: " + evaluatorId + ", Applicant: " + applicantId + " 로 Evaluation을 찾을 수 없습니다.");
    }
}