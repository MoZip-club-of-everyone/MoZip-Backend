package com.mozip.mozip.domain.applicant.controller;

import com.mozip.mozip.domain.applicant.service.ApplicantManager;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswersForApplicantResDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswersForApplicantResDto;
import com.mozip.mozip.domain.applicant.dto.ApplicantListResponse;
import com.mozip.mozip.domain.applicant.dto.InterviewApplicantData;
import com.mozip.mozip.domain.applicant.dto.PaperApplicantData;
import com.mozip.mozip.domain.applicant.dto.UpdateApplicantStatusRequest;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluatedApplicantData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluatedApplicantData;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.applicant.dto.ApplicantInfoResponse;
import com.mozip.mozip.domain.applicant.dto.ApplicantInfoRequest;
import com.mozip.mozip.global.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mozip/{mozip_id}/applicants")
public class ApplicantController {
    private final ApplicantManager applicantManager;

    // 지원자 생성
    @PostMapping
    public ResponseEntity<ApplicantInfoResponse> postApplicant(
            @PathVariable("mozip_id") String mozipId,
            @RequestBody ApplicantInfoRequest request) {
        log.info("POST 지원자 생성: mozip-{}", mozipId);
        return ResponseEntity.ok(applicantManager.createApplicantByMozipId(request, mozipId));
    }

    // 지원자 필수 정보 조회
    @GetMapping
    public ResponseEntity<ApplicantInfoResponse> getApplicantInfo(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId) {
        log.info("GET 지원자 필수 정보 조회: mozip-{}", mozipId);
        User user = ((CustomUserDetails) authentication.getPrincipal()).user();
        return ResponseEntity.ok(applicantManager.getApplicantInfoByMozipId(user, mozipId));
    }

    // 서류 지원자 목록 조회
    @GetMapping("/papers")
    public ResponseEntity<ApplicantListResponse<PaperApplicantData>> getApplicantList(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        User evaluator = ((CustomUserDetails) authentication.getPrincipal()).user();
        log.info("GET 서류 지원자 목록 조회: mozip-{}", mozipId);
        return ResponseEntity.ok(applicantManager.getApplicantListByMozipId(evaluator, mozipId, sortBy, order));
    }

    // 서류 지원서 목록 조회
    @GetMapping("/papers/answers")
    public ResponseEntity<PaperAnswersForApplicantResDto> getPaperAnswers(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "applicant-id", required = false) String applicantId,
            @RequestParam(value = "question-id", required = false) String questionId) {
        User evaluator = ((CustomUserDetails) authentication.getPrincipal()).user();
        log.info("GET 서류 지원서 목록 조회: mozip-{}, evaluator-{}", mozipId, evaluator.getId());
        return ResponseEntity.ok(applicantManager.getPaperAnswersByMozipId(evaluator, mozipId, applicantId, questionId));
    }

    // 서류 평가 점수 목록 조회
    @GetMapping("/papers/evaluations")
    public ResponseEntity<ApplicantListResponse<PaperEvaluatedApplicantData>> getPaperEvaluations(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        User evaluator = ((CustomUserDetails) authentication.getPrincipal()).user();
        log.info("GET 서류 평가 점수 목록 조회: mozip-{}", mozipId);
        return ResponseEntity.ok(applicantManager.getPaperEvaluationsByMozipId(evaluator, mozipId, sortBy, order));
    }

    // 서류 합격자 목록 조회
    @GetMapping("/interviews")
    public ResponseEntity<ApplicantListResponse<InterviewApplicantData>> getPaperPassedApplicants(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        User evaluator = ((CustomUserDetails) authentication.getPrincipal()).user();
        log.info("GET 서류 합격자 목록 조회: mozip-{}", mozipId);
        return ResponseEntity.ok(applicantManager.getInterviewApplicantListByMozipId(evaluator, mozipId, sortBy, order));
    }

    // 면접 기록 목록 조회
    @GetMapping("/interviews/answers")
    public ResponseEntity<InterviewAnswersForApplicantResDto> getInterviewAnswers(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "applicant-id", required = false) String applicantId,
            @RequestParam(value = "question-id", required = false) String questionId) {
        User evaluator = ((CustomUserDetails) authentication.getPrincipal()).user();
        log.info("GET 면접 기록 목록 조회: mozip-{}, evaluator-{}", mozipId, evaluator.getId());
        return ResponseEntity.ok(applicantManager.getInterviewAnswersByMozipId(evaluator, mozipId, applicantId, questionId));
    }

    // 면접 평가 점수 목록 조회
    @GetMapping("/interviews/evaluations")
    public ResponseEntity<ApplicantListResponse<InterviewEvaluatedApplicantData>> getInterviewEvaluations(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        User evaluator = ((CustomUserDetails) authentication.getPrincipal()).user();
        log.info("GET 면접 평가 점수 목록 조회: mozip-{}", mozipId);
        return ResponseEntity.ok(applicantManager.getInterviewEvaluationsByMozipId(evaluator, mozipId, sortBy, order));
    }
}