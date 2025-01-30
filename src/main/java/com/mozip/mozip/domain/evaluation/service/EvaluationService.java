package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.repository.EvaluationRepository;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerService;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.exception.EvaluationNotFoundException;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerService;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    private final PaperAnswerService paperAnswerService;
    private final InterviewAnswerService interviewAnswerService;

    public List<Evaluation> getEvaluationsByApplicant(Applicant applicant) {
        return evaluationRepository.findByApplicant(applicant);
    }

    public Evaluation getEvaluationByApplicantAndEvaluator(Applicant applicant, User evaluator) {
        return evaluationRepository.findByApplicantAndEvaluator(applicant, evaluator)
                .orElseThrow(() -> new EvaluationNotFoundException(applicant.getId(), evaluator.getId()));
    }

    @Transactional
    public void updatePaperScore(User evaluator, String paperAnswerId, int score) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        Evaluation evaluation = getEvaluationByApplicantAndEvaluator(paperAnswer.getApplicant(), evaluator);
        evaluation.setPaperScore(score);
        evaluationRepository.save(evaluation);
    }

    @Transactional
    public void updateInterviewScore(User evaluator, String interviewAnswerId, int score) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        Evaluation evaluation = getEvaluationByApplicantAndEvaluator(interviewAnswer.getApplicant(), evaluator);
        evaluation.setInterviewScore(score);
        evaluationRepository.save(evaluation);
    }
}