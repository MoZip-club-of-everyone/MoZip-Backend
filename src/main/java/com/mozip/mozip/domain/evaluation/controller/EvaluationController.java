package com.mozip.mozip.domain.evaluation.controller;

import com.mozip.mozip.domain.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EvaluationController {
    private final EvaluationService evaluationService;
}
