package com.mozip.mozip.domain.paperQuestion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaperQuestionUpdateReqDto {
    private String question;
    private String details;
    private boolean isRequired;
}