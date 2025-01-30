package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperEvaluationDetailsResponse {
    private String applicantName;
    private Integer paperScore;
    private Long questionNo;
    private String question;
    private String answer;
    private List<CommentData> comments;
    private List<MemoData> memos;

    public static PaperEvaluationDetailsResponse from(Applicant applicant, Integer paperScore, PaperQuestion question, PaperAnswer answer, List<CommentData> comments, List<MemoData> memos) {
        return PaperEvaluationDetailsResponse.builder()
                .applicantName(applicant.getUser().getRealname())
                .paperScore(paperScore)
                .questionNo(question.getQuestionNo())
                .question(question.getQuestion())
                .answer(answer.getAnswer())
                .comments(comments)
                .memos(memos)
                .build();
    }
}
