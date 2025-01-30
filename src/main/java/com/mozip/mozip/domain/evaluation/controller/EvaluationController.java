package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.dto.UpdatePaperScoreRequest;
import com.mozip.mozip.domain.evaluation.service.EvaluationService;
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
    private final EvaluationService evaluationService;

    // 서류 점수 입력
    @PatchMapping("/paper-answers/{paper_answer_id}/evaluations/score")
    public ResponseEntity<Void> patchPaperScore(
            Authentication authentication,
            @PathVariable("paper_answer_id") String paperAnswerId,
            @RequestBody UpdatePaperScoreRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PATCH 서류 점수 입력: evaluator-{}", evaluator.getId());
        evaluationService.updatePaperScore(evaluator, paperAnswerId, request.getScore());
        return ResponseEntity.ok().build();
    }

    // 인터뷰 점수 입력
    @PatchMapping("/interviews/answers/{interview_answer_id}/evaluations/score")
    public ResponseEntity<Void> patchInterviewScore(
            Authentication authentication,
            @PathVariable("interview_answer_id") String interviewAnswerId,
            @RequestBody UpdatePaperScoreRequest request) {
        User evaluator = (User) authentication.getPrincipal();
        log.info("PATCH 인터뷰 점수 입력: evaluator-{}", evaluator.getId());
        evaluationService.updateInterviewScore(evaluator, interviewAnswerId, request.getScore());
        return ResponseEntity.ok().build();
    }
}
