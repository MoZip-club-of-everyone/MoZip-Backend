package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.entity.InterviewComment;
import com.mozip.mozip.domain.evaluation.entity.PaperComment;
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
public class CommentManager {
    private final CommentService commentService;
    private final PaperAnswerService paperAnswerService;
    private final InterviewAnswerService interviewAnswerService;

    @Transactional
    public void addPaperComment(User evaluator, String paperAnswerId, String comment) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        PaperComment paperComment = PaperComment.builder()
                .writer(evaluator)
                .paperAnswer(paperAnswer)
                .comment(comment)
                .build();
        commentService.savePaperComment(paperComment);
    }

    @Transactional
    public void updatePaperComment(User evaluator, String paperAnswerId, String commentId, String comment) {
        PaperComment paperComment = commentService.getPaperCommentByCommentId(commentId);
        paperComment.setComment(comment);
        commentService.savePaperComment(paperComment);
    }

    @Transactional
    public void deletePaperComment(User evaluator, String paperAnswerId, String commentId) {
        PaperComment paperComment = commentService.getPaperCommentByCommentId(commentId);
        commentService.deletePaperComment(paperComment);
    }

    @Transactional
    public void addInterviewComment(User evaluator, String interviewAnswerId, String comment) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        InterviewComment interviewComment = InterviewComment.builder()
                .writer(evaluator)
                .interviewAnswer(interviewAnswer)
                .comment(comment)
                .build();
        commentService.saveInterviewComment(interviewComment);
    }

    @Transactional
    public void updateInterviewComment(User evaluator, String interviewAnswerId, String commentId, String comment) {
        InterviewComment interviewComment = commentService.getInterviewCommentByCommentId(commentId);
        interviewComment.setComment(comment);
        commentService.saveInterviewComment(interviewComment);
    }

    @Transactional
    public void deleteInterviewComment(User evaluator, String interviewAnswerId, String commentId) {
        InterviewComment interviewComment = commentService.getInterviewCommentByCommentId(commentId);
        commentService.deleteInterviewComment(interviewComment);
    }
}
