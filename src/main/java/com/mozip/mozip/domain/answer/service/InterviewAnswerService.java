package com.mozip.mozip.domain.answer.service;

import com.mozip.mozip.domain.answer.repository.InterviewAnswerRepository;
import com.mozip.mozip.domain.answer.repository.PaperAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewAnswerService {
    private final InterviewAnswerRepository interviewAnswerRepository;
    private final PaperAnswerRepository paperAnswerRepository;
}
