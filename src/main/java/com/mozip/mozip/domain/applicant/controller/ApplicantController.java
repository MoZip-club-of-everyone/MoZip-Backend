package com.mozip.mozip.domain.applicant.controller;

import com.mozip.mozip.domain.answer.dto.PaperAnswersResDto;
import com.mozip.mozip.domain.applicant.dto.*;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.global.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mozip/{mozip_id}/applicants")
public class ApplicantController {
    private final ApplicantService applicantService;

    // 서류 지원자 목록 조회
    @GetMapping
    public ResponseEntity<ApplicantListResponse> getApplicantList(
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(applicantService.getApplicantListByMozipId(mozipId, sortBy, order));
    }

    // 서류 지원서 목록 조회
    @GetMapping("/paper-answers")
    public ResponseEntity<PaperAnswersResDto> getPaperAnswers(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "applicant-id", required = false) String applicantId,
            @RequestParam(value = "question-id", required = false) String questionId) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(applicantService.getPaperAnswersByMozipId(userId, mozipId, applicantId, questionId));
    }
//
//    // 서류 평가 점수 목록 조회
//    @GetMapping("/paper-evaluations")
//    public ResponseEntity<PaperEvaluationListResponse> getPaperEvaluations(
//            @PathVariable("mozip_id") String mozipId,
//            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
//            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
//        return ResponseEntity.ok(applicantService.getPaperEvaluationsByMozipId(mozipId, sortBy, order));
//    }
//
//    // 서류 합격자 목록 조회
//    @GetMapping("/paper-passed")
//    public ResponseEntity<PaperPassedListResponse> getPaperPassedApplicants(
//            @PathVariable("mozip_id") String mozipId,
//            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
//            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
//        return ResponseEntity.ok(applicantService.getPaperPassedApplicantsByMozipId(mozipId, sortBy, order));
//    }
//
//    // 면접 기록 목록 조회
//    @GetMapping("/interview-answers")
//    public ResponseEntity<InterviewAnswerListResponse> getInterviewAnswers(
//            @PathVariable("mozip_id") String mozipId,
//            @RequestParam(value = "applicant-id", required = false) String applicantId,
//            @RequestParam(value = "question-id", required = false) String questionId) {
//        return ResponseEntity.ok(applicantService.getInterviewAnswersByMozipId(mozipId, applicantId, questionId));
//    }
//
//    // 면접 평가 점수 목록 조회
//    @GetMapping("/interview-evaluations")
//    public ResponseEntity<InterviewEvaluationListResponse> getInterviewEvaluations(
//            @PathVariable("mozip_id") String mozipId,
//            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
//            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
//        return ResponseEntity.ok(applicantService.getInterviewEvaluationsByMozipId(mozipId, sortBy, order));
//    }
}