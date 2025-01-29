package com.mozip.mozip.domain.interviewAnswer.controller;

import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerCreateReqDto;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerResDto;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerUpdateReqDto;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerManager;
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
@RequestMapping("/interviews/answers")
@RequiredArgsConstructor
public class InterviewAnswerController {

    private final InterviewAnswerManager interviewAnswerManager;

    // 지원자에 대한 모든 응답 조회
    @GetMapping("/applicants/{applicant_id}")
    public ResponseEntity<HashMap<String, Object>> getAnswersByApplicantId(@PathVariable("applicant_id") String applicantId) {
        List<InterviewAnswerResDto> answers = interviewAnswerManager.getInterviewAnswersByApplicantId(applicantId);
        HashMap<String, Object> response = new HashMap<>();
        response.put("list", answers);
        return ResponseEntity.ok(response);
    }

    // 특정 질문에 대한 모든 응답 조회
    @GetMapping("/questions/{question_id}")
    public ResponseEntity<HashMap<String, Object>> getAnswersByQuestionId(@PathVariable("question_id") String questionId) {
        List<InterviewAnswerResDto> answers = interviewAnswerManager.getInterviewAnswersByQuestionId(questionId);
        HashMap<String, Object> response = new HashMap<>();
        response.put("list", answers);
        return ResponseEntity.ok(response);
    }

    // 지원자의 특정 질문에 대한 응답 조회
    @GetMapping("/applicants/{applicant_id}/questions/{question_id}")
    public InterviewAnswerResDto getAnswerByQuestionAndApplicant(@PathVariable("applicant_id") String applicantId,
                                                                 @PathVariable("question_id") String questionId) {
        return interviewAnswerManager.getInterviewAnswerByQuestionAndApplicant(questionId, applicantId);
    }

    // id로 응답 조회
    @GetMapping("/{answer_id}")
    public InterviewAnswerResDto getAnswerById(@PathVariable("answer_id") String answerId) {
        return interviewAnswerManager.getInterviewAnswerById(answerId);
    }

    @PostMapping
    public InterviewAnswerResDto createAnswer(@RequestBody InterviewAnswerCreateReqDto requestDto) {
        return interviewAnswerManager.createAnswer(requestDto);
    }

    @PutMapping("/{answer_id}")
    public InterviewAnswerResDto updateAnswer(@PathVariable("answer_id") String answerId,
                                              @RequestBody InterviewAnswerUpdateReqDto requestDto) {
        return interviewAnswerManager.updateAnswer(answerId, requestDto);
    }

    @DeleteMapping("/{answer_id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("answer_id") String answerId) {
        interviewAnswerManager.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}