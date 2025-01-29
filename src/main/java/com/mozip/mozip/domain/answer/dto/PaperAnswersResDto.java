package com.mozip.mozip.domain.answer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.PaperQuestion.dto.PaperQuestionWithAnswersData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperAnswersResDto {
    private List<PaperQuestionWithAnswersData> questions;

    public static PaperAnswersResDto from(List<PaperQuestionWithAnswersData> questions) {
        return PaperAnswersResDto.builder()
                .questions(questions)
                .build();
    }
}