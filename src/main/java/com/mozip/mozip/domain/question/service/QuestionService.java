package com.mozip.mozip.domain.question.service;

import com.mozip.mozip.domain.question.repository.InterviewQuestionRepository;
import com.mozip.mozip.domain.question.repository.PaperQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final PaperQuestionRepository paperQuestionRepository;
}
