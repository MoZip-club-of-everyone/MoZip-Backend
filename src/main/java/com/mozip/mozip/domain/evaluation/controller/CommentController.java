package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.dto.CommentRequest;
import com.mozip.mozip.domain.evaluation.service.CommentManager;
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
public class CommentController {
    private final CommentManager commentManager;

    // 서류 코멘트 작성
    @PostMapping("/papers/answers/{paper_answer_id}/evaluations/comments")
    public ResponseEntity<Void> postPaperComment(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @RequestBody CommentRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 서류 코멘트 작성: evaluator-{}", evaluator.getId());
        commentManager.addPaperComment(evaluator, paperAnswerId, request.getComment());
        return ResponseEntity.ok().build();
    }

    // 서류 코멘트 수정
    @PutMapping("/papers/answers/{paper_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<Void> putPaperComment(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("comment_id") String commentId,
            @RequestBody CommentRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 서류 코멘트 수정: evaluator-{}", evaluator.getId());
        commentManager.updatePaperComment(evaluator, paperAnswerId, commentId, request.getComment());
        return ResponseEntity.ok().build();
    }

    // 서류 코멘트 삭제
    @DeleteMapping("/papers/answers/{paper_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<Void> deletePaperComment(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("comment_id") String commentId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 서류 코멘트 삭제: evaluator-{}", evaluator.getId());
        commentManager.deletePaperComment(evaluator, paperAnswerId, commentId);
        return ResponseEntity.ok().build();
    }

    // 인터뷰 코멘트 작성
    @PostMapping("/interviews/answers/{interview_answer_id}/evaluations/comments")
    public ResponseEntity<Void> postInterviewComment(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @RequestBody CommentRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 인터뷰 코멘트 작성: evaluator-{}", evaluator.getId());
        commentManager.addInterviewComment(evaluator, interviewAnswerId, request.getComment());
        return ResponseEntity.ok().build();
    }

    // 인터뷰 코멘트 수정
    @PutMapping("/interviews/answers/{interview_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<Void> putInterviewComment(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("comment_id") String commentId,
            @RequestBody CommentRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 인터뷰 코멘트 수정: evaluator-{}", evaluator.getId());
        commentManager.updateInterviewComment(evaluator, interviewAnswerId, commentId, request.getComment());
        return ResponseEntity.ok().build();
    }

    // 인터뷰 코멘트 삭제
    @DeleteMapping("/interviews/answers/{interview_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<Void> deleteInterviewComment(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("comment_id") String commentId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 인터뷰 코멘트 삭제: evaluator-{}", evaluator.getId());
        commentManager.deleteInterviewComment(evaluator, interviewAnswerId, commentId);
        return ResponseEntity.ok().build();
    }
} 