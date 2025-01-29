package com.mozip.mozip.domain.answer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperAnswerData {
    private String applicantId;
    private String realname;
    private String answer;
    private int paperScore;

    public static PaperAnswerData from(PaperAnswer answer, int paperScore) {
        return PaperAnswerData.builder()
                .applicantId(answer.getApplicant().getId())
                .realname(answer.getApplicant().getUser().getRealname())
                .answer(answer.getAnswer())
                .paperScore(paperScore)
                .build();
    }
}