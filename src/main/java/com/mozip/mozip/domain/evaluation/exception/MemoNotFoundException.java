package com.mozip.mozip.domain.evaluation.exception;

public class MemoNotFoundException extends RuntimeException {
    public MemoNotFoundException(String memoId) {
        super("Memo: " + memoId + "로 메모를 찾을 수 없습니다.");
    }
} 