package com.mozip.mozip.domain.paperQuestion.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperQuestionResDto {
    private String mozipId;
    private String questionId;
    private String question;
    private String details;
    private PaperQuestionType type;
    private boolean isRequired;

    public static PaperQuestionResDto fromEntity(PaperQuestion paperQuestion) {
        return new PaperQuestionResDto(
                paperQuestion.getMozip().getId(),
                paperQuestion.getId(),
                paperQuestion.getQuestion(),
                paperQuestion.getDetails(),
                paperQuestion.getType(),
                paperQuestion.isRequired()
        );
    }
}