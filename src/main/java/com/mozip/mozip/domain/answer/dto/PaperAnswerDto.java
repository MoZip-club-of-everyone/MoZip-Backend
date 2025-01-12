package com.mozip.mozip.domain.answer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperAnswerDto {
    private String applicantId;
    private String realname;
    private String answer;
    private int score;

    public static PaperAnswerDto from(PaperAnswer answer, int score) {
        return PaperAnswerDto.builder()
                .applicantId(answer.getApplicant().getId())
                .realname(answer.getApplicant().getUser().getRealname())
                .answer(answer.getAnswer())
                .score(score)
                .build();
    }
}