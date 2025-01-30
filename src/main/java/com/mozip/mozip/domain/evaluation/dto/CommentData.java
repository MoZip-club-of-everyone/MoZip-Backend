package com.mozip.mozip.domain.evaluation.dto;

import com.mozip.mozip.domain.evaluation.entity.InterviewComment;
import com.mozip.mozip.domain.evaluation.entity.PaperComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentData {
    private String comment;
    private String writer;
    private LocalDateTime date;

    public static CommentData from(PaperComment comment) {
        return CommentData.builder()
                .comment(comment.getComment())
                .writer(comment.getWriter().getRealname())
                .date(comment.getUpdatedAt())
                .build();
    }

    public static CommentData from(InterviewComment comment) {
        return CommentData.builder()
                .comment(comment.getComment())
                .writer(comment.getWriter().getRealname())
                .date(comment.getUpdatedAt())
                .build();
    }
} 