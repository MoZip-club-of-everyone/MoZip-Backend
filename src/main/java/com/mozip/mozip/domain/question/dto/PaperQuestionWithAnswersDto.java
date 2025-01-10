package com.mozip.mozip.domain.question.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.answer.dto.PaperAnswerDto;
import com.mozip.mozip.domain.question.entity.PaperQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperQuestionWithAnswersDto {
    private String questionId;
    private String question;
    private List<PaperAnswerDto> answers;

    public static PaperQuestionWithAnswersDto from(PaperQuestion question, List<PaperAnswerDto> answers) {
        return PaperQuestionWithAnswersDto.builder()
                .questionId(question.getId())
                .question(question.getQuestion())
                .answers(answers)
                .build();
    }
}