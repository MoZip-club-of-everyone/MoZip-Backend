package com.mozip.mozip.domain.paperQuestion.dto;

import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaperQuestionResDto {
    private String mozipId;
    private String question;
    private String details;
    private boolean isRequired;

    public static PaperQuestionResDto fromEntity(PaperQuestion paperQuestion) {
        return new PaperQuestionResDto(
                paperQuestion.getMozip().getId(),
                paperQuestion.getQuestion(),
                paperQuestion.getDetails(),
                paperQuestion.isRequired()
        );
    }
}