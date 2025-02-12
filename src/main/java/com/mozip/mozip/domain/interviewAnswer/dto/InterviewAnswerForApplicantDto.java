package com.mozip.mozip.domain.interviewAnswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterviewAnswerForApplicantDto {
    private String applicantId;
    private String realname;
    private String answer;
    private Integer score;

    public static InterviewAnswerForApplicantDto from(InterviewAnswer answer, Integer score) {
        return InterviewAnswerForApplicantDto.builder()
                .applicantId(answer.getApplicant().getId())
                .realname(answer.getApplicant().getUser().getRealname())
                .answer(answer.getAnswer())
                .score(score)
                .build();
    }
}