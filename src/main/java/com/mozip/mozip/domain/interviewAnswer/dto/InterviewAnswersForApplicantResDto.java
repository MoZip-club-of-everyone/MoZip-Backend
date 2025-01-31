package com.mozip.mozip.domain.interviewAnswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionWithAnswersDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterviewAnswersForApplicantResDto {
    private List<InterviewQuestionWithAnswersDto> questions;

    public static InterviewAnswersForApplicantResDto from(List<InterviewQuestionWithAnswersDto> questions) {
        return InterviewAnswersForApplicantResDto.builder()
                .questions(questions)
                .build();
    }
}