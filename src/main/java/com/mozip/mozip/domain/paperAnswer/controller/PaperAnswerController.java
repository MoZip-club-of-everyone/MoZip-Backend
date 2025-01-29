package com.mozip.mozip.domain.paperAnswer.controller;

import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerCreateReqDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerResDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerUpdateReqDto;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerManager;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/papers/answers")
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
        return paperAnswerManager.createAnswer(requestDto);
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