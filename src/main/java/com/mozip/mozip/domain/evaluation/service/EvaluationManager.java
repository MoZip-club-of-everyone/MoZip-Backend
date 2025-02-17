package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.applicant.dto.UpdateApplicantStatusRequest;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.applicant.exception.ApplicantException;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.domain.club.service.ClubService;
import com.mozip.mozip.domain.evaluation.dto.CommentData;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluationDetailsResponse;
import com.mozip.mozip.domain.evaluation.dto.MemoData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluationDetailsResponse;
import com.mozip.mozip.domain.evaluation.entity.EvaluateArea;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerService;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import com.mozip.mozip.domain.interviewQuestion.service.InterviewQuestionService;
import com.mozip.mozip.domain.mozip.service.MozipService;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerService;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionService;
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.exception.PositionException;
import com.mozip.mozip.domain.user.service.PositionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationManager {
    private final EvaluationService evaluationService;
    private final PaperQuestionService paperQuestionService;
    private final PaperAnswerService paperAnswerService;
    private final InterviewQuestionService interviewQuestionService;
    private final InterviewAnswerService interviewAnswerService;
    private final ApplicantService applicantService;
    private final CommentService commentService;
    private final MemoService memoService;
    private final ClubService clubService;
    private final PositionService positionService;

    // 서류 합불 상태 수정
    @Transactional
    public void updateApplicantPaperStatuses(User evaluator, UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            checkEvaluable(evaluator, applicant);
            if (applicant.getPaperStatus() == EvaluationStatus.UNEVALUATED) {
                throw ApplicantException.paperNotEvaluated(applicant);
            }
            applicant.setPaperStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });

    }

    // 면접 합불 상태 수정
    @Transactional
    public void updateApplicantInterviewStatuses(User evaluator, UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            checkEvaluable(evaluator, applicant);
            if (applicant.getInterviewStatus() == EvaluationStatus.UNEVALUATED) {
                throw ApplicantException.interviewNotEvaluated(applicant);
            }
            applicant.setInterviewStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });

    }

    // 서류 점수 입력
    @Transactional
    public void updatePaperScore(User evaluator, String paperAnswerId, int score) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        Applicant applicant = paperAnswer.getApplicant();
        checkEvaluable(evaluator, applicant);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        evaluation.setPaperScore(score);
        evaluationService.saveEvaluation(evaluation);
        calculateAverageScore(applicant, EvaluateArea.PAPER);
        calculateStandardDeviation(applicant, EvaluateArea.PAPER);
        applicantService.saveApplicant(applicant);
        checkAllEvaluated(applicant);
    }

    // 면접 점수 입력
    @Transactional
    public void updateInterviewScore(User evaluator, String interviewAnswerId, int score) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        Applicant applicant = interviewAnswer.getApplicant();
        checkInterviewEvaluable(evaluator, applicant);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        evaluation.setInterviewScore(score);
        evaluationService.saveEvaluation(evaluation);
        calculateAverageScore(applicant, EvaluateArea.INTERVIEW);
        calculateStandardDeviation(applicant, EvaluateArea.INTERVIEW);
        applicantService.saveApplicant(applicant);
        checkAllEvaluated(applicant);
    }

    // 특정 서류 응답 평가 조회
    @Transactional(readOnly = true)
    public PaperEvaluationDetailsResponse getPaperEvaluationDetails(User evaluator, String applicantId, String questionId) {
        Applicant applicant = applicantService.getApplicantById(applicantId);
        PaperQuestion paperQuestion = paperQuestionService.getPaperQuestionById(questionId);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerByQuestionAndApplicant(questionId, applicantId)
                .orElseThrow(() -> new EntityNotFoundException("PaperAnswer가 없습니다 : " + questionId + ", " + applicantId));
        List<CommentData> comments = commentService.getCommentsByPaperAnswer(paperAnswer);
        List<MemoData> memos = memoService.getMemosByPaperAnswer(paperAnswer);
        return PaperEvaluationDetailsResponse.from(applicant, evaluation.getPaperScore(), paperQuestion, paperAnswer, comments, memos);
    }

    // 특정 면접 기록 평가 조회
    @Transactional(readOnly = true)
    public InterviewEvaluationDetailsResponse getInterviewEvaluationDetails(User evaluator, String applicantId, String questionId) {
        Applicant applicant = applicantService.getApplicantById(applicantId);
        InterviewQuestion interviewQuestion = interviewQuestionService.getInterviewQuestionById(questionId);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerByQuestionAndApplicant(questionId, applicantId)
                .orElseThrow(() -> new EntityNotFoundException("InterviewAnswer가 없습니다 : " + questionId + ", " + applicantId));
        List<CommentData> comments = commentService.getCommentsByInterviewAnswer(interviewAnswer);
        List<MemoData> memos = memoService.getMemosByInterviewAnswer(interviewAnswer);
        return InterviewEvaluationDetailsResponse.from(applicant, evaluation.getInterviewScore(), interviewQuestion, interviewAnswer, comments, memos);
    }

    @Transactional
    public void checkAllEvaluated(Applicant applicant) {
        long totalEvaluators = clubService.countEvaluatorsByClub(applicant.getMozip().getClub());
        long evaluatedPaperScores = evaluationService.countEvaluatedPaperScore(applicant);
        if (totalEvaluators == evaluatedPaperScores) {
            applicant.setPaperStatus(EvaluationStatus.EVALUATED);
        } else {
            applicant.setPaperStatus(EvaluationStatus.UNEVALUATED);
        }
        applicantService.saveApplicant(applicant);
    }

    private void checkEvaluable(User evaluator, Applicant applicant) {
        Position position = positionService.getPositionByUserAndClub(evaluator, applicant.getMozip().getClub());
        if (!positionService.checkEvaluablePosition(position)) {
            throw PositionException.notEvaluable(position);
        }
        if (!applicant.getIsRegistered()) {
            throw ApplicantException.notRegistered(applicant);
        }
    }

    private void checkInterviewEvaluable(User evaluator, Applicant applicant) {
        if (applicant.getPaperStatus() == EvaluationStatus.UNEVALUATED) {
            throw ApplicantException.paperNotEvaluated(applicant);
        }
        checkEvaluable(evaluator, applicant);
    }

    // 평균 계산
    private void calculateAverageScore(Applicant applicant, EvaluateArea evaluateArea) {
        Double average = switch (evaluateArea) {
            case PAPER -> evaluationService.calculateAveragePaperScore(applicant);
            case INTERVIEW -> evaluationService.calculateAverageInterviewScore(applicant);
        };
        Double roundedAverage = average * 10.0 / 10.0;
        switch (evaluateArea) {
            case PAPER -> applicant.setPaperScoreAverage(roundedAverage);
            case INTERVIEW -> applicant.setInterviewScoreAverage(roundedAverage);
        }
    }

    // 표준편차 계산
    private void calculateStandardDeviation(Applicant applicant, EvaluateArea evaluateArea) {
        Double standardDeviation = switch (evaluateArea) {
            case PAPER -> evaluationService.calculateStandardDeviationPaperScore(applicant);
            case INTERVIEW -> evaluationService.calculateStandardDeviationInterviewScore(applicant);
        };
        Double roundedStandardDeviation = standardDeviation * 10.0 / 10.0;
        switch (evaluateArea) {
            case PAPER -> applicant.setPaperScoreStandardDeviation(roundedStandardDeviation);
            case INTERVIEW -> applicant.setInterviewStandardDeviation(roundedStandardDeviation);
        }
    }
}
