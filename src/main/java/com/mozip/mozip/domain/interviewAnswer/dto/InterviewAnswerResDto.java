package com.mozip.mozip.domain.interviewAnswer.dto;

import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InterviewAnswerResDto {
    private String applicantId;
    private String questionId;
    private String answer;
    
    public static InterviewAnswerResDto fromEntity(InterviewAnswer interviewAnswer) {
        return new InterviewAnswerResDto(
                interviewAnswer.getApplicant().getId(),
                interviewAnswer.getInterviewQuestion().getId(),
                interviewAnswer.getAnswer());
    }
}