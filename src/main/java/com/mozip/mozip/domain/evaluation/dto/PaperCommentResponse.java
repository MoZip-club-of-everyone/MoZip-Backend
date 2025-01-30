package com.mozip.mozip.domain.evaluation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaperCommentResponse {
    private List<CommentData> comments;

    public static PaperCommentResponse from(List<CommentData> comments) {
        return PaperCommentResponse.builder()
                .comments(comments)
                .build();
    }
} 