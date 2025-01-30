package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import com.mozip.mozip.domain.evaluation.service.MemoService;
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
    private final MemoService memoService;

    // 서류 메모 작성
    @PostMapping("/paper-answers/{paper_answer_id}/evaluations/memos")
    public ResponseEntity<PaperMemo> postPaperMemo(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @RequestBody String memo) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 서류 메모 작성: evaluator-{}", evaluator.getId());
        PaperMemo paperMemo = memoService.addPaperMemo(evaluator, paperAnswerId, memo);
        return ResponseEntity.ok(paperMemo);
    }

    // 서류 메모 수정
    @PutMapping("/paper-answers/{paper_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<PaperMemo> putPaperMemo(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("memo_id") String memoId,
            @RequestBody String memo) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 서류 메모 수정: evaluator-{}", evaluator.getId());
        PaperMemo updatedMemo = memoService.updatePaperMemo(evaluator, paperAnswerId, memoId, memo);
        return ResponseEntity.ok(updatedMemo);
    }

    // 서류 메모 삭제
    @DeleteMapping("/paper-answers/{paper_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<Void> deletePaperMemo(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("memo_id") String memoId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 서류 메모 삭제: evaluator-{}", evaluator.getId());
        memoService.deletePaperMemo(evaluator, paperAnswerId, memoId);
        return ResponseEntity.ok().build();
    }

    // 인터뷰 메모 작성
    @PostMapping("/interviews/answers/{interview_answer_id}/evaluations/memos")
    public ResponseEntity<PaperMemo> postInterviewMemo(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @RequestBody String memo) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 인터뷰 메모 작성: evaluator-{}", evaluator.getId());
        PaperMemo interviewMemo = memoService.addInterviewMemo(evaluator, interviewAnswerId, memo);
        return ResponseEntity.ok(interviewMemo);
    }

    // 인터뷰 메모 수정
    @PutMapping("/interviews/answers/{interview_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<PaperMemo> putInterviewMemo(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("memo_id") String memoId,
            @RequestBody String memo) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 인터뷰 메모 수정: evaluator-{}", evaluator.getId());
        PaperMemo updatedMemo = memoService.updateInterviewMemo(evaluator, interviewAnswerId, memoId, memo);
        return ResponseEntity.ok(updatedMemo);
    }

    // 인터뷰 메모 삭제
    @DeleteMapping("/interviews/answers/{interview_answer_id}/evaluations/memos/{memo_id}")
    public ResponseEntity<Void> deleteInterviewMemo(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("memo_id") String memoId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 인터뷰 메모 삭제: evaluator-{}", evaluator.getId());
        memoService.deleteInterviewMemo(evaluator, interviewAnswerId, memoId);
        return ResponseEntity.ok().build();
    }
} 