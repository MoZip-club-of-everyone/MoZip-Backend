package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.repository.EvaluationRepository;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    public Evaluation createEvaluation(User evaluator, Applicant applicant) {
        Evaluation evaluation = Evaluation.builder()
                .evaluator(evaluator)
                .applicant(applicant)
                .build();
        return saveEvaluation(evaluation);
    }

    public List<Evaluation> getEvaluationsByApplicant(Applicant applicant) {
        return evaluationRepository.findByApplicant(applicant);
    }

    public Evaluation getEvaluationByApplicantAndEvaluator(Applicant applicant, User evaluator) {
        return evaluationRepository.findByApplicantAndEvaluator(applicant, evaluator)
                .orElseGet(() -> createEvaluation(evaluator, applicant));
    }

    public Evaluation saveEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public long countEvaluatedPaperScore(Applicant applicant) {
        return evaluationRepository.countByApplicantAndPaperScoreIsNotNull(applicant);
    }

    public Double calculateAveragePaperScore(Applicant applicant) {
        return evaluationRepository.calculateAveragePaperScoreByApplicant(applicant);
    }

    public Double calculateAverageInterviewScore(Applicant applicant) {
        return evaluationRepository.calculateAverageInterviewScoreByApplicant(applicant);
    }

    public Double calculateStandardDeviationPaperScore(Applicant applicant) {
        return evaluationRepository.calculatePaperScoreStandardDeviationByApplicant(applicant);
    }

    public Double calculateStandardDeviationInterviewScore(Applicant applicant) {
        return evaluationRepository.calculateInterviewScoreStandardDeviationByApplicant(applicant);
    }
}