package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.dto.MemoRequest;
import com.mozip.mozip.domain.evaluation.service.MemoManager;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {
    private final MemoManager memoManager;

    // 서류 메모 작성
    @PostMapping("/papers/answers/{paper_answer_id}/evaluations/memos")
    public ResponseEntity<Void> postPaperMemo(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @RequestBody MemoRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 서류 메모 작성: evaluator-{}", evaluator.getId());
        memoManager.addPaperMemo(evaluator, paperAnswerId, request.getMemo());
        return ResponseEntity.ok().build();
    }

    // 서류 메모 수정
    @PutMapping("/papers/answers/{paper_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<Void> putPaperMemo(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("memo_id") String memoId,
            @RequestBody MemoRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 서류 메모 수정: evaluator-{}", evaluator.getId());
        memoManager.updatePaperMemo(evaluator, paperAnswerId, memoId, request.getMemo());
        return ResponseEntity.ok().build();
    }

    // 서류 메모 삭제
    @DeleteMapping("/papers/answers/{paper_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<Void> deletePaperMemo(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("memo_id") String memoId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 서류 메모 삭제: evaluator-{}", evaluator.getId());
        memoManager.deletePaperMemo(evaluator, paperAnswerId, memoId);
        return ResponseEntity.ok().build();
    }

    // 인터뷰 메모 작성
    @PostMapping("/interviews/answers/{interview_answer_id}/evaluations/memos")
    public ResponseEntity<Void> postInterviewMemo(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @RequestBody MemoRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 인터뷰 메모 작성: evaluator-{}", evaluator.getId());
        memoManager.addInterviewMemo(evaluator, interviewAnswerId, request.getMemo());
        return ResponseEntity.ok().build();
    }

    // 인터뷰 메모 수정
    @PutMapping("/interviews/answers/{interview_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<Void> putInterviewMemo(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("memo_id") String memoId,
            @RequestBody MemoRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 인터뷰 메모 수정: evaluator-{}", evaluator.getId());
        memoManager.updateInterviewMemo(evaluator, interviewAnswerId, memoId, request.getMemo());
        return ResponseEntity.ok().build();
    }

    // 인터뷰 메모 삭제
    @DeleteMapping("/interviews/answers/{interview_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<Void> deleteInterviewMemo(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("memo_id") String memoId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 인터뷰 메모 삭제: evaluator-{}", evaluator.getId());
        memoManager.deleteInterviewMemo(evaluator, interviewAnswerId, memoId);
        return ResponseEntity.ok().build();
    }
} 