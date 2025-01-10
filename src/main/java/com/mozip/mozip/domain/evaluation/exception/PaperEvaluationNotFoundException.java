package com.mozip.mozip.domain.evaluation.exception;

public class PaperEvaluationNotFoundException extends RuntimeException {
    public PaperEvaluationNotFoundException(String evaluationId, String paperAnswerId) {
        super("Evaluation: " + evaluationId + ", PaperAnswer: " + paperAnswerId + " 로 PaperEvaluation을 찾을 수 없습니다.");
    }
}