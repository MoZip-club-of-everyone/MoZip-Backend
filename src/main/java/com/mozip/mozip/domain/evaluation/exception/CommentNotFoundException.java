package com.mozip.mozip.domain.evaluation.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String commentId) {
        super("Comment: " + commentId + "로 Evaluation을 찾을 수 없습니다.");
    }
}
