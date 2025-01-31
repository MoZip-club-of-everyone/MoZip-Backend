package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.dto.CommentData;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PaperCommentRepository paperCommentRepository;
    private final InterviewCommentRepository interviewCommentRepository;

    public PaperComment getPaperCommentByCommentId(String commentId) {
        return paperCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    public InterviewComment getInterviewCommentByCommentId(String commentId) {
        return interviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    public List<CommentData> getCommentsByPaperAnswer(PaperAnswer paperAnswer) {
        List<PaperComment> paperComments = paperCommentRepository.findByPaperAnswer(paperAnswer);
        return paperComments.stream()
                .map(CommentData::from)
                .toList();
    }

    public List<CommentData> getCommentsByInterviewAnswer(InterviewAnswer interviewAnswer) {
        List<InterviewComment> interviewComments = interviewCommentRepository.findByInterviewAnswer(interviewAnswer);
        return interviewComments.stream()
                .map(CommentData::from)
                .toList();
    }

    public void savePaperComment(PaperComment paperComment) {
        paperCommentRepository.save(paperComment);
    }

    public void deletePaperComment(PaperComment paperComment) {
        paperCommentRepository.delete(paperComment);
    }

    public void saveInterviewComment(InterviewComment interviewComment) {
        interviewCommentRepository.save(interviewComment);
    }

    public void deleteInterviewComment(InterviewComment interviewComment) {
        interviewCommentRepository.delete(interviewComment);
    }
} 