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
            validateEvaluationStatusUpdatable(evaluator, applicant, EvaluateArea.PAPER);
            applicant.setPaperStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });
    }

    // 면접 합불 상태 수정
    @Transactional
    public void updateApplicantInterviewStatuses(User evaluator, UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            validateEvaluationStatusUpdatable(evaluator, applicant, EvaluateArea.INTERVIEW);
            applicant.setInterviewStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });
    }

    // 서류 점수 입력
    @Transactional
    public void updatePaperScore(User evaluator, String paperAnswerId, int score) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        Applicant applicant = paperAnswer.getApplicant();
        validateScoreUpdatable(evaluator, applicant, EvaluateArea.PAPER);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        evaluation.setPaperScore(score);
        evaluationService.saveEvaluation(evaluation);
        updateAverageAndStandardDeviation(applicant, EvaluateArea.PAPER);
        checkAllEvaluated(applicant, EvaluateArea.PAPER);
    }

    // 면접 점수 입력
    @Transactional
    public void updateInterviewScore(User evaluator, String interviewAnswerId, int score) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        Applicant applicant = interviewAnswer.getApplicant();
        validateScoreUpdatable(evaluator, applicant, EvaluateArea.INTERVIEW);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        evaluation.setInterviewScore(score);
        evaluationService.saveEvaluation(evaluation);
        updateAverageAndStandardDeviation(applicant, EvaluateArea.INTERVIEW);
        checkAllEvaluated(applicant, EvaluateArea.INTERVIEW);
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

    private void validateEvaluationStatusUpdatable(User evaluator, Applicant applicant, EvaluateArea evaluateArea) {
        verifyEvaluatorPermission(evaluator, applicant);
        EvaluationStatus status = switch (evaluateArea) {
            case PAPER -> applicant.getPaperStatus();
            case INTERVIEW -> applicant.getInterviewStatus();
        };
        if (status == EvaluationStatus.UNEVALUATED) {
            throw ApplicantException.notEvaluated(applicant);
        }
    }

    private void validateScoreUpdatable(User evaluator, Applicant applicant, EvaluateArea evaluateArea) {
        verifyEvaluatorPermission(evaluator, applicant);
        EvaluationStatus status = switch (evaluateArea) {
            case PAPER -> applicant.getPaperStatus();
            case INTERVIEW -> applicant.getInterviewStatus();
        };
        if (status == EvaluationStatus.HOLD) {
            throw ApplicantException.evaluationOnHold(applicant);
        }
    }

    private void verifyEvaluatorPermission(User evaluator, Applicant applicant) {
        if (!applicant.getIsRegistered()) {
            throw ApplicantException.notRegistered(applicant);
        }
        Position position = positionService.getPositionByUserAndClub(evaluator, applicant.getMozip().getClub());
        if (!positionService.checkEvaluablePosition(position)) {
            throw PositionException.notEvaluable(position);
        }
    }

    private void updateAverageAndStandardDeviation(Applicant applicant, EvaluateArea evaluateArea) {
        Double average;
        Double standardDeviation;
        switch (evaluateArea) {
            case PAPER -> {
                average = evaluationService.calculateAveragePaperScore(applicant);
                standardDeviation = evaluationService.calculateStandardDeviationPaperScore(applicant);
                applicant.setPaperScoreAverage(average);
                applicant.setPaperScoreStandardDeviation(standardDeviation);
            }
            case INTERVIEW -> {
                average = evaluationService.calculateAverageInterviewScore(applicant);
                standardDeviation = evaluationService.calculateStandardDeviationInterviewScore(applicant);
                applicant.setInterviewScoreAverage(average);
                applicant.setInterviewStandardDeviation(standardDeviation);
            }
        }
        applicantService.saveApplicant(applicant);
    }

    public void checkAllEvaluated(Applicant applicant, EvaluateArea evaluateArea) {
        long totalEvaluatorsCount = clubService.countEvaluatorsByClub(applicant.getMozip().getClub());
        long evaluatedScoresCount = getEvaluatedScoresCount(applicant, evaluateArea);
        if (totalEvaluatorsCount == evaluatedScoresCount) {
            setEvaluationStatus(applicant, evaluateArea, EvaluationStatus.EVALUATED);
        }
        applicantService.saveApplicant(applicant);
    }

    private long getEvaluatedScoresCount(Applicant applicant, EvaluateArea evaluateArea) {
        return switch (evaluateArea) {
            case PAPER -> evaluationService.countEvaluatedPaperScore(applicant);
            case INTERVIEW -> evaluationService.countEvaluatedInterviewScore(applicant);
        };
    }

    private void setEvaluationStatus(Applicant applicant, EvaluateArea evaluateArea, EvaluationStatus status) {
        switch (evaluateArea) {
            case PAPER -> applicant.setPaperStatus(status);
            case INTERVIEW -> applicant.setInterviewStatus(status);
        }
    }
}
