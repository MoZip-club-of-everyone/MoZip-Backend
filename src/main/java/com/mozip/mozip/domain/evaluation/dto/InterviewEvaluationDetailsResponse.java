package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterviewEvaluationDetailsResponse {
    private String applicantName;
    private Integer interviewScore;
    private Long questionNo;
    private String question;
    private String answer;
    private List<CommentData> comments;
    private List<MemoData> memos;

    public static InterviewEvaluationDetailsResponse from(Applicant applicant, Integer interviewScore, InterviewQuestion question, InterviewAnswer answer, List<CommentData> comments, List<MemoData> memos) {
        return InterviewEvaluationDetailsResponse.builder()
                .applicantName(applicant.getUser().getRealname())
                .interviewScore(interviewScore)
                .questionNo(question.getQuestionNo())
                .question(question.getQuestion())
                .answer(answer.getAnswer())
                .comments(comments)
                .memos(memos)
                .build();
    }
}
