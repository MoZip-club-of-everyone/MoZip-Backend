package com.mozip.mozip.domain.interviewQuestion.controller;

import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionCreateReqDto;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionResDto;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionUpdateReqDto;
import com.mozip.mozip.domain.interviewQuestion.service.InterviewQuestionManager;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interviews/questions")
@RequiredArgsConstructor
public class InterviewQuestionController {

    private final InterviewQuestionManager interviewQuestionManager;

    @GetMapping
    public HashMap<String, List<InterviewQuestionResDto>> getQuestionsByMozipId(@RequestParam("mozip_id") String mozipId) {
        List<InterviewQuestionResDto> interviewQuestionResDtoList = interviewQuestionManager.getInterviewQuestionsByMozipId(mozipId);
        HashMap<String, List<InterviewQuestionResDto>> response = new HashMap<>();
        response.put("list", interviewQuestionResDtoList);
        return response;
    }

    @GetMapping("/{question_id}")
    public InterviewQuestionResDto getQuestionById(@PathVariable("question_id") String questionId) {
        return interviewQuestionManager.getInterviewQuestionById(questionId);
    }

    @PostMapping
    public InterviewQuestionResDto createQuestion(@RequestBody InterviewQuestionCreateReqDto requestDto) {
        return interviewQuestionManager.createInterviewQuestion(requestDto);
    }

    @PutMapping("/{question_id}")
    public InterviewQuestionResDto updateQuestion(@PathVariable("question_id") String questionId,
                                                  @RequestBody InterviewQuestionUpdateReqDto requestDto) {
        return interviewQuestionManager.updateInterviewQuestion(questionId, requestDto);
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("question_id") String questionId) {
        interviewQuestionManager.deleteInterviewQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}