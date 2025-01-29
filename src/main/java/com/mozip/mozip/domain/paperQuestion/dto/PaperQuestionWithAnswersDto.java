package com.mozip.mozip.domain.paperQuestion.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerForApplicantDto;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperQuestionWithAnswersDto {
    private String questionId;
    private String question;
    private List<PaperAnswerForApplicantDto> answers;

    public static PaperQuestionWithAnswersDto from(PaperQuestion question, List<PaperAnswerForApplicantDto> answers) {
        return PaperQuestionWithAnswersDto.builder()
                .questionId(question.getId())
                .question(question.getQuestion())
                .answers(answers)
                .build();
    }
}