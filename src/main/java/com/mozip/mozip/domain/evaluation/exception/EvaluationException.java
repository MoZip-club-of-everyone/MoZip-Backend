package com.mozip.mozip.domain.evaluation.exception;

public class EvaluationException extends RuntimeException {
    public EvaluationException(String applicantId, String evaluatorId) {
        super("Applicant: " + applicantId + ", Evaluator: " + evaluatorId + " 로 Evaluation을 찾을 수 없습니다.");
    }
}