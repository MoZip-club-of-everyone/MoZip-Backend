package com.mozip.mozip.domain.paperAnswer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaperAnswerCreateReqDto {
    private String applicantId;
    private String questionId;
    private String answer;
}