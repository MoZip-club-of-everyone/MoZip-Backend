package com.mozip.mozip.domain.paperAnswer.dto;

import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaperAnswerResDto {
    private String applicantId;
    private String questionId;
    private String answer;
    
    public static PaperAnswerResDto fromEntity(PaperAnswer paperAnswer) {
        return new PaperAnswerResDto(
                paperAnswer.getApplicant().getId(), 
                paperAnswer.getPaperQuestion().getId(), 
                paperAnswer.getAnswer());
    }
}