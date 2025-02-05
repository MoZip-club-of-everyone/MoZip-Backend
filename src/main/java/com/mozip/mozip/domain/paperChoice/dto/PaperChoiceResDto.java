package com.mozip.mozip.domain.paperChoice.dto;

import com.mozip.mozip.domain.paperChoice.entity.PaperChoice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaperChoiceResDto {
    private String choiceId;
    private String choice;

    public static PaperChoiceResDto entityToDto(PaperChoice paperChoice) {
        return new PaperChoiceResDto(paperChoice.getId(), paperChoice.getChoice());
    }
}
