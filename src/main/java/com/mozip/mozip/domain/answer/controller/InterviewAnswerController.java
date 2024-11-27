package com.mozip.mozip.domain.answer.controller;

import com.mozip.mozip.domain.answer.service.InterviewAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InterviewAnswerController {
    private final InterviewAnswerService interviewAnswerService;
}
