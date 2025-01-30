package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.entity.PaperComment;
import com.mozip.mozip.domain.evaluation.service.CommentService;
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
    private final CommentService commentService;

    // 서류 코멘트 작성
    @PostMapping("/paper-answers/{paper_answer_id}/evaluations/comments")
    public ResponseEntity<PaperComment> postPaperComment(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @RequestBody String comment) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 서류 코멘트 작성: evaluator-{}", evaluator.getId());
        PaperComment paperComment = commentService.addPaperComment(evaluator, paperAnswerId, comment);
        return ResponseEntity.ok(paperComment);
    }

    // 서류 코멘트 수정
    @PutMapping("/paper-answers/{paper_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<PaperComment> putPaperComment(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("comment_id") String commentId,
            @RequestBody String comment) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 서류 코멘트 수정: evaluator-{}", evaluator.getId());
        PaperComment updatedComment = commentService.updatePaperComment(evaluator, paperAnswerId, commentId, comment);
        return ResponseEntity.ok(updatedComment);
    }

    // 서류 코멘트 삭제
    @DeleteMapping("/paper-answers/{paper_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<Void> deletePaperComment(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @PathVariable("comment_id") String commentId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 서류 코멘트 삭제: evaluator-{}", evaluator.getId());
        commentService.deletePaperComment(evaluator, paperAnswerId, commentId);
        return ResponseEntity.ok().build();
    }

    // 인터뷰 코멘트 작성
    @PostMapping("/interviews/answers/{interview_answer_id}/evaluations/comments")
    public ResponseEntity<PaperComment> postInterviewComment(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @RequestBody String comment) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("POST 인터뷰 코멘트 작성: evaluator-{}", evaluator.getId());
        PaperComment interviewComment = commentService.addInterviewComment(evaluator, interviewAnswerId, comment);
        return ResponseEntity.ok(interviewComment);
    }

    // 인터뷰 코멘트 수정
    @PutMapping("/interviews/answers/{interview_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<PaperComment> putInterviewComment(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("comment_id") String commentId,
            @RequestBody String comment) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PUT 인터뷰 코멘트 수정: evaluator-{}", evaluator.getId());
        PaperComment updatedComment = commentService.updateInterviewComment(evaluator, interviewAnswerId, commentId, comment);
        return ResponseEntity.ok(updatedComment);
    }

    // 인터뷰 코멘트 삭제
    @DeleteMapping("/interviews/answers/{interview_answer_id}/evaluations/comments/{comment_id}")
    public ResponseEntity<Void> deleteInterviewComment(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @PathVariable("comment_id") String commentId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("DELETE 인터뷰 코멘트 삭제: evaluator-{}", evaluator.getId());
        commentService.deleteInterviewComment(evaluator, interviewAnswerId, commentId);
        return ResponseEntity.ok().build();
    }
} 