package com.mozip.mozip.domain.interviewQuestion.dto;

import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InterviewQuestionResDto {
    private String mozipId;
    private String question;
    private String details;
    private boolean isRequired;

    public static InterviewQuestionResDto fromEntity(InterviewQuestion interviewQuestion) {
        return new InterviewQuestionResDto(
                interviewQuestion.getMozip().getId(),
                interviewQuestion.getQuestion(),
                interviewQuestion.getDetails(),
                interviewQuestion.isRequired()
        );
    }
}