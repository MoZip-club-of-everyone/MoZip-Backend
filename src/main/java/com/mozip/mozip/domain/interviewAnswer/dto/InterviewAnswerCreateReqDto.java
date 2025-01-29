package com.mozip.mozip.domain.interviewAnswer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterviewAnswerCreateReqDto {
    private String applicantId;
    private String questionId;
    private String answer;
}