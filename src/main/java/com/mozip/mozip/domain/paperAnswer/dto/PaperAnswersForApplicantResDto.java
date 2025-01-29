package com.mozip.mozip.domain.paperAnswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionWithAnswersDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperAnswersForApplicantResDto {
    private List<PaperQuestionWithAnswersDto> questions;

    public static PaperAnswersForApplicantResDto from(List<PaperQuestionWithAnswersDto> questions) {
        return PaperAnswersForApplicantResDto.builder()
                .questions(questions)
                .build();
    }
}