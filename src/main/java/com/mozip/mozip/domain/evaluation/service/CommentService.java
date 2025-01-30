package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.entity.PaperComment;
import com.mozip.mozip.domain.evaluation.repository.PaperCommentRepository;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PaperCommentRepository paperCommentRepository;

    public PaperComment addPaperComment(User evaluator, String paperAnswerId, String comment) {
        // Implement logic to add a paper comment
        return null;
    }

    public PaperComment updatePaperComment(User evaluator, String paperAnswerId, String commentId, String comment) {
        // Implement logic to update a paper comment
        return null;
    }

    public void deletePaperComment(User evaluator, String paperAnswerId, String commentId) {
        // Implement logic to delete a paper comment
    }

    public PaperComment addInterviewComment(User evaluator, String interviewAnswerId, String comment) {
        // Implement logic to add an interview comment
        return null;
    }

    public PaperComment updateInterviewComment(User evaluator, String interviewAnswerId, String commentId, String comment) {
        // Implement logic to update an interview comment
        return null;
    }

    public void deleteInterviewComment(User evaluator, String interviewAnswerId, String commentId) {
        // Implement logic to delete an interview comment
    }
} 