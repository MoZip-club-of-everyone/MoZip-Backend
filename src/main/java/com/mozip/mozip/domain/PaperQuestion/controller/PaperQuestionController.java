package com.mozip.mozip.domain.PaperQuestion.controller;

import com.mozip.mozip.domain.PaperQuestion.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.PaperQuestion.dto.PaperQuestionUpdateReqDto;
import com.mozip.mozip.domain.PaperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.PaperQuestion.service.PaperQuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/papers/questions")
@RequiredArgsConstructor
public class PaperQuestionController {

    private final PaperQuestionService questionService;

    @GetMapping
    public ResponseEntity<List<PaperQuestion>> getQuestionsByMozipId(@RequestParam("mozip_id") String mozipId) {
        return ResponseEntity.ok(questionService.getPaperQuestionsByMozipId(mozipId));
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<PaperQuestion> getQuestionById(@PathVariable("question_id") String questionId) {
        return ResponseEntity.ok(questionService.getPaperQuestionById(questionId));
    }

    @PostMapping
    public ResponseEntity<PaperQuestion> createQuestion(@RequestBody PaperQuestionCreateReqDto requestDto) {
        PaperQuestion createdQuestion = questionService.createPaperQuestion(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PutMapping("/{question_id}")
    public ResponseEntity<PaperQuestion> updateQuestion(@PathVariable("question_id") String questionId,
                                                        @RequestBody PaperQuestionUpdateReqDto requestDto) {
        PaperQuestion updatedQuestion = questionService.updatePaperQuestion(questionId, requestDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("question_id") String questionId) {
        questionService.deletePaperQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}