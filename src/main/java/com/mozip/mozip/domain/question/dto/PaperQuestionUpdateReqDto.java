package com.mozip.mozip.domain.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaperQuestionUpdateReqDto {
    private String question;
    private String details;
    private boolean isRequired;
}