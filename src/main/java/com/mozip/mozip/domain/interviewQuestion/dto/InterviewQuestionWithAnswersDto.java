package com.mozip.mozip.domain.interviewQuestion.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerForApplicantDto;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterviewQuestionWithAnswersDto {
    private String questionId;
    private String question;
    private List<InterviewAnswerForApplicantDto> answers;

    public static InterviewQuestionWithAnswersDto from(InterviewQuestion question, List<InterviewAnswerForApplicantDto> answers) {
        return InterviewQuestionWithAnswersDto.builder()
                .questionId(question.getId())
                .question(question.getQuestion())
                .answers(answers)
                .build();
    }
}