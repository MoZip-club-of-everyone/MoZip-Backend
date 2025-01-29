package com.mozip.mozip.domain.interviewQuestion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterviewQuestionCreateReqDto {
    private String mozipId;
    private String question;
    private String details;
    private boolean isRequired;
}