package com.mozip.mozip.domain.paperQuestion.controller;

import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionResDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionUpdateReqDto;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionManager;
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
@RequestMapping("/papers/questions")
@RequiredArgsConstructor
public class PaperQuestionController {

    private final PaperQuestionManager paperQuestionManager;

    @GetMapping
    public List<PaperQuestionResDto> getQuestionsByMozipId(@RequestParam("mozip_id") String mozipId) {
        return paperQuestionManager.getPaperQuestionsByMozipId(mozipId);
    }

    @GetMapping("/{question_id}")
    public PaperQuestionResDto getQuestionById(@PathVariable("question_id") String questionId) {
        return paperQuestionManager.getPaperQuestionById(questionId);
    }

    @PostMapping
    public PaperQuestionResDto createQuestion(@RequestBody PaperQuestionCreateReqDto requestDto) {
        return paperQuestionManager.createPaperQuestion(requestDto);
    }

    @PutMapping("/{question_id}")
    public PaperQuestionResDto updateQuestion(@PathVariable("question_id") String questionId,
                                              @RequestBody PaperQuestionUpdateReqDto requestDto) {
        return paperQuestionManager.updatePaperQuestion(questionId, requestDto);
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("question_id") String questionId) {
        paperQuestionManager.deletePaperQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}