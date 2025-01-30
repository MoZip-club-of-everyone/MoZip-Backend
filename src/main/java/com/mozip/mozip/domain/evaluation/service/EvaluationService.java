package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.exception.EvaluationNotFoundException;
import com.mozip.mozip.domain.evaluation.repository.EvaluationRepository;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    public List<Evaluation> getEvaluationsByApplicant(Applicant applicant) {
        return evaluationRepository.findByApplicant(applicant);
    }

    public Evaluation getEvaluationByApplicantAndEvaluator(Applicant applicant, User evaluator) {
        return evaluationRepository.findByApplicantAndEvaluator(applicant, evaluator)
                .orElseThrow(() -> new EvaluationNotFoundException(applicant.getId(), evaluator.getId()));
    }
}