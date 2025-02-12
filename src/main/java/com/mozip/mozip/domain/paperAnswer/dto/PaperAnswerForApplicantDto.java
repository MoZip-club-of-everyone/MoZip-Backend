package com.mozip.mozip.domain.paperAnswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperAnswerForApplicantDto {
    private String applicantId;
    private String realname;
    private String answer;
    private Integer score;

    public static PaperAnswerForApplicantDto from(PaperAnswer answer, Integer score) {
        return PaperAnswerForApplicantDto.builder()
                .applicantId(answer.getApplicant().getId())
                .realname(answer.getApplicant().getUser().getRealname())
                .answer(answer.getAnswer())
                .score(score)
                .build();
    }
}