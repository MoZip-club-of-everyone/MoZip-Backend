package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.entity.InterviewComment;
import com.mozip.mozip.domain.evaluation.entity.PaperComment;
import com.mozip.mozip.domain.evaluation.exception.CommentNotFoundException;
import com.mozip.mozip.domain.evaluation.repository.InterviewCommentRepository;
import com.mozip.mozip.domain.evaluation.repository.PaperCommentRepository;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerService;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerService;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PaperCommentRepository paperCommentRepository;
    private final InterviewCommentRepository interviewCommentRepository;
    
    private final PaperAnswerService paperAnswerService;
    private final InterviewAnswerService interviewAnswerService;

    private PaperComment getPaperCommentByCommentId(String commentId) {
        return paperCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    private InterviewComment getInterviewCommentByCommentId(String commentId) {
        return interviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }
    
    @Transactional
    public void addPaperComment(User evaluator, String paperAnswerId, String comment) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        PaperComment paperComment = PaperComment.builder()
                .writer(evaluator)
                .paperAnswer(paperAnswer)
                .comment(comment)
                .build();
        paperCommentRepository.save(paperComment);
    }
    
    @Transactional
    public void updatePaperComment(User evaluator, String paperAnswerId, String commentId, String comment) {
        PaperComment paperComment = getPaperCommentByCommentId(commentId);
        paperComment.setComment(comment);
        paperCommentRepository.save(paperComment);
    }

    @Transactional
    public void deletePaperComment(User evaluator, String paperAnswerId, String commentId) {
        PaperComment paperComment = getPaperCommentByCommentId(commentId);
        paperCommentRepository.delete(paperComment);
    }

    @Transactional
    public void addInterviewComment(User evaluator, String interviewAnswerId, String comment) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        InterviewComment interviewComment = InterviewComment.builder()
                .writer(evaluator)
                .interviewAnswer(interviewAnswer)
                .comment(comment)
                .build();
        interviewCommentRepository.save(interviewComment);
    }

    @Transactional
    public void updateInterviewComment(User evaluator, String interviewAnswerId, String commentId, String comment) {
        InterviewComment interviewComment = getInterviewCommentByCommentId(commentId);
        interviewComment.setComment(comment);
        interviewCommentRepository.save(interviewComment);
    }

    @Transactional
    public void deleteInterviewComment(User evaluator, String interviewAnswerId, String commentId) {
        InterviewComment interviewComment = getInterviewCommentByCommentId(commentId);
        interviewCommentRepository.delete(interviewComment);
    }
} 