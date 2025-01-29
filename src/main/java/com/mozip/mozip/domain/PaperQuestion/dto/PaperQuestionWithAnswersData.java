package com.mozip.mozip.domain.PaperQuestion.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.answer.dto.PaperAnswerData;
import com.mozip.mozip.domain.PaperQuestion.entity.PaperQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperQuestionWithAnswersData {
    private String questionId;
    private String question;
    private List<PaperAnswerData> answers;

    public static PaperQuestionWithAnswersData from(PaperQuestion question, List<PaperAnswerData> answers) {
        return PaperQuestionWithAnswersData.builder()
                .questionId(question.getId())
                .question(question.getQuestion())
                .answers(answers)
                .build();
    }
}