package com.mozip.mozip.domain.paperAnswer.controller;

import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerCreateReqDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerResDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerUpdateReqDto;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/papers/answers")
@RequiredArgsConstructor
public class PaperAnswerController {

    private final PaperAnswerManager paperAnswerManager;

    // 지원자에 대한 모든 응답 조회
    @GetMapping("/applicants/{applicant_id}")
    public ResponseEntity<HashMap<String, Object>> getAnswersByApplicantId(@PathVariable("applicant_id") String applicantId) {
        List<PaperAnswerResDto> answers = paperAnswerManager.getPaperAnswersByApplicantId(applicantId);
        HashMap<String, Object> response = new HashMap<>();
        response.put("list", answers);
        return ResponseEntity.ok(response);
    }

    // 특정 질문에 대한 모든 응답 조회
    @GetMapping("/questions/{question_id}")
    public ResponseEntity<HashMap<String, Object>> getAnswersByQuestionId(@PathVariable("question_id") String questionId) {
        List<PaperAnswerResDto> answers = paperAnswerManager.getPaperAnswersByQuestionId(questionId);
        HashMap<String, Object> response = new HashMap<>();
        response.put("list", answers);
        return ResponseEntity.ok(response);
    }

    // 지원자의 특정 질문에 대한 응답 조회
    @GetMapping("/applicants/{applicant_id}/questions/{question_id}")
    public PaperAnswerResDto getAnswerByQuestionAndApplicant(@PathVariable("applicant_id") String applicantId,
                                                             @PathVariable("question_id") String questionId) {
        return paperAnswerManager.getPaperAnswerByQuestionAndApplicant(questionId, applicantId);
    }

    // id로 응답 조회
    @GetMapping("/{answer_id}")
    public PaperAnswerResDto getAnswerById(@PathVariable("answer_id") String answerId) {
        return paperAnswerManager.getPaperAnswerById(answerId);
    }

    @PostMapping
    public PaperAnswerResDto createAnswer(@RequestBody PaperAnswerCreateReqDto requestDto) {
        log.info("POST 서류 응답 추가: applicant-{}", requestDto.getApplicantId());
        return paperAnswerManager.createAnswer(requestDto);
    }

    @PostMapping("/register/applicants/{applicant_id}")
    public String registerApplicant(@PathVariable("applicant_id") String applicantId) {
        paperAnswerManager.registerAnswer(applicantId);
        return "정상적으로 등록되었습니다.";
    }

    @PutMapping("/{answer_id}")
    public PaperAnswerResDto updateAnswer(@PathVariable("answer_id") String answerId,
                                          @RequestBody PaperAnswerUpdateReqDto requestDto) {
        return paperAnswerManager.updateAnswer(answerId, requestDto);
    }

    @DeleteMapping("/{answer_id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("answer_id") String answerId) {
        paperAnswerManager.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}