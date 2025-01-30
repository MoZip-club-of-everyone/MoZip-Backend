package com.mozip.mozip.domain.evaluation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InterviewCommentResponse {
    private List<CommentData> comments;

    public static InterviewCommentResponse from(List<CommentData> comments) {
        return InterviewCommentResponse.builder()
                .comments(comments)
                .build();
    }
} 