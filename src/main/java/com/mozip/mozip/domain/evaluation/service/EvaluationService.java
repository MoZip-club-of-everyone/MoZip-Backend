package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.repository.EvaluationRepository;
import com.mozip.mozip.domain.evaluation.repository.InterviewCommentRepository;
import com.mozip.mozip.domain.evaluation.repository.PaperCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final InterviewCommentRepository interviewCommentRepository;
    private final PaperCommentRepository paperCommentRepository;
}