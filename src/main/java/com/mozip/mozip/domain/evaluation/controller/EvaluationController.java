package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluationDetailsResponse;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluationDetailsResponse;
import com.mozip.mozip.domain.evaluation.dto.UpdateScoreRequest;
import com.mozip.mozip.domain.evaluation.service.EvaluationManager;
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
public class EvaluationController {
    private final EvaluationManager evaluationManager;

    // 서류 점수 입력
    @PatchMapping("/papers/answers/{paper_answer_id}/evaluations/score")
    public ResponseEntity<Void> patchPaperScore(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @RequestBody UpdateScoreRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PATCH 서류 점수 입력: evaluator-{}", evaluator.getId());
        evaluationManager.updatePaperScore(evaluator, paperAnswerId, request.getScore());
        return ResponseEntity.ok().build();
    }

    // 인터뷰 점수 입력
    @PatchMapping("/interviews/answers/{interview_answer_id}/evaluations/score")
    public ResponseEntity<Void> patchInterviewScore(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @RequestBody UpdateScoreRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PATCH 인터뷰 점수 입력: evaluator-{}", evaluator.getId());
        evaluationManager.updateInterviewScore(evaluator, interviewAnswerId, request.getScore());
        return ResponseEntity.ok().build();
    }

    // 특정 서류 응답 평가 조회
    @GetMapping("/papers/answers/{paper_answer_id}/evaluations")
    public ResponseEntity<PaperEvaluationDetailsResponse> getInterviewAnswers(
            Authentication authentication,
            @RequestParam("applicant-id") String applicantId,
            @RequestParam("question-id") String questionId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("GET 특정 서류 응답 평가 조회: evaluator-{}", evaluator.getId());
        PaperEvaluationDetailsResponse response = evaluationManager.getPaperEvaluationDetails(evaluator, applicantId, questionId);
        return ResponseEntity.ok(response);
    }

    // 특정 면접 기록 평가 조회
    @GetMapping("/interviews/answers/{interview_answer_id}/evaluations")
    public ResponseEntity<InterviewEvaluationDetailsResponse> getInterviewEvaluation(
            Authentication authentication,
            @RequestParam("applicant-id") String applicantId,
            @RequestParam("question-id") String questionId) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("GET 특정 면접 기록 평가 조회: evaluator-{}", evaluator.getId());
        InterviewEvaluationDetailsResponse response = evaluationManager.getInterviewEvaluationDetails(evaluator, applicantId, questionId);
        return ResponseEntity.ok(response);
    }
}
