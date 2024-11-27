package com.mozip.mozip.domain.answer.controller;

import com.mozip.mozip.domain.answer.dto.PaperAnswerCreateReqDto;
import com.mozip.mozip.domain.answer.dto.PaperAnswerUpdateReqDto;
import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import com.mozip.mozip.domain.answer.service.PaperAnswerService;
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
@RequestMapping("/papers/answers")
@RequiredArgsConstructor
public class PaperAnswerController {

    private final PaperAnswerService paperAnswerService;

    @GetMapping
    public ResponseEntity<List<PaperAnswer>> getAnswersByApplicantId(@RequestParam("applicant_id") String applicantId) {
        return ResponseEntity.ok(paperAnswerService.getAnswersByApplicantId(applicantId));
    }

    @GetMapping("/{answer_id}")
    public ResponseEntity<PaperAnswer> getAnswerById(@PathVariable("answer_id") String answerId) {
        return ResponseEntity.ok(paperAnswerService.getAnswerById(answerId));
    }

    @PostMapping
    public ResponseEntity<PaperAnswer> createAnswer(@RequestBody PaperAnswerCreateReqDto requestDto) {
        PaperAnswer createdAnswer = paperAnswerService.createAnswer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    @PutMapping("/{answer_id}")
    public ResponseEntity<PaperAnswer> updateAnswer(@PathVariable("answer_id") String answerId,
                                                    @RequestBody PaperAnswerUpdateReqDto requestDto) {
        PaperAnswer updatedAnswer = paperAnswerService.updateAnswer(answerId, requestDto);
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/{answer_id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("answer_id") String answerId) {
        paperAnswerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}