package com.mozip.mozip.domain.applicant.controller;

import com.mozip.mozip.domain.answer.dto.PaperAnswersResDto;
import com.mozip.mozip.domain.applicant.dto.ApplicantListResponse;
import com.mozip.mozip.domain.applicant.dto.InterviewApplicantData;
import com.mozip.mozip.domain.applicant.dto.PaperApplicantData;
import com.mozip.mozip.domain.applicant.dto.UpdateApplicantStatusRequest;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluatedApplicantData;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api//mozip/{mozip_id}/applicants")
public class ApplicantController {
    private final ApplicantService applicantService;

    // 서류 지원자 목록 조회
    @GetMapping
    public ResponseEntity<ApplicantListResponse<PaperApplicantData>> getApplicantList(
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(applicantService.getApplicantListByMozipId(mozipId, sortBy, order));
    }

    // 서류 지원서 목록 조회
    @GetMapping("/papers/answers")
    public ResponseEntity<PaperAnswersResDto> getPaperAnswers(
            Authentication authentication,
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "applicant-id", required = false) String applicantId,
            @RequestParam(value = "question-id", required = false) String questionId) {
        User evaluator = (User) authentication.getPrincipal();
        return ResponseEntity.ok(applicantService.getPaperAnswersByMozipId(evaluator, mozipId, applicantId, questionId));
    }

    // 서류 합불 상태 수정
    @PatchMapping("/papers/answers/status")
    public ResponseEntity<Void> updateApplicantPaperStatuses(
            @PathVariable("mozip_id") String mozipId,
            @RequestBody UpdateApplicantStatusRequest request) {
        applicantService.updateApplicantPaperStatuses(request);
        return ResponseEntity.ok().build();
    }


    // 서류 평가 점수 목록 조회
    @GetMapping("/papers/evaluations")
    public ResponseEntity<ApplicantListResponse<PaperEvaluatedApplicantData>> getPaperEvaluations(
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(applicantService.getPaperEvaluationsByMozipId(mozipId, sortBy, order));
    }

    // 서류 합격자 목록 조회
    @GetMapping("/papers/passed")
    public ResponseEntity<ApplicantListResponse<InterviewApplicantData>> getPaperPassedApplicants(
            @PathVariable("mozip_id") String mozipId,
            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(applicantService.getInterviewApplicantListByMozipId(mozipId, sortBy, order));
    }
//
//    // 면접 기록 목록 조회
//    @GetMapping("/interviews/answers")
//    public ResponseEntity<InterviewAnswerListResponse> getInterviewAnswers(
//            @PathVariable("mozip_id") String mozipId,
//            @RequestParam(value = "applicant-id", required = false) String applicantId,
//            @RequestParam(value = "question-id", required = false) String questionId) {
//        return ResponseEntity.ok(applicantService.getInterviewAnswersByMozipId(mozipId, applicantId, questionId));
//    }
//
//    // 면접 평가 점수 목록 조회
//    @GetMapping("/interviews/evaluations")
//    public ResponseEntity<InterviewEvaluationListResponse> getInterviewEvaluations(
//            @PathVariable("mozip_id") String mozipId,
//            @RequestParam(value = "sort-by", required = false, defaultValue = "number") String sortBy,
//            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
//        return ResponseEntity.ok(applicantService.getInterviewEvaluationsByMozipId(mozipId, sortBy, order));
//    }
}