package com.mozip.mozip.domain.paperQuestion.dto;

import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaperQuestionUpdateReqDto {
    private PaperQuestionType type;
    private String question;
    private String details;
    private boolean isRequired;
}