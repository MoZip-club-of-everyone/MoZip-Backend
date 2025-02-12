package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.applicant.exception.ApplicantException;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.domain.club.service.ClubService;
import com.mozip.mozip.domain.evaluation.dto.CommentData;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluationDetailsResponse;
import com.mozip.mozip.domain.evaluation.dto.MemoData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluationDetailsResponse;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerService;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import com.mozip.mozip.domain.interviewQuestion.service.InterviewQuestionService;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerService;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionService;
import com.mozip.mozip.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 서류 점수 입력
    @Transactional
    public void updatePaperScore(User evaluator, String paperAnswerId, int score) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        Applicant applicant = paperAnswer.getApplicant();
        checkPaperEvaluable(evaluator, applicant);
        Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(applicant, evaluator);
        evaluation.setPaperScore(score);
        evaluationService.saveEvaluation(evaluation);
        applicant.setPaperScoreAverage(applicant.calculatePaperAverage());
        applicant.setPaperScoreStandardDeviation(applicant.calculatePaperStandardDeviation());
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
        applicant.setInterviewScoreAverage(applicant.calculateInterviewAverage());
        applicant.setInterviewStandardDeviation(applicant.calculateInterviewStandardDeviation());
        applicantService.saveApplicant(applicant);
        checkAllEvaluated(applicant);
    }

    private void checkPaperEvaluable(User evaluator, Applicant applicant) {
        if (applicant.getPaperStatus() != EvaluationStatus.UNEVALUATED) {
            throw ApplicantException.paperEvaluated(applicant);
        }
    }

    private void checkInterviewEvaluable(User evaluator, Applicant applicant) {
        checkPaperEvaluable(evaluator, applicant);
        if (applicant.getInterviewStatus() != EvaluationStatus.UNEVALUATED) {
            throw ApplicantException.interviewEvaluated(applicant);
        }
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
            applicantService.saveApplicant(applicant);
        }
    }
}
