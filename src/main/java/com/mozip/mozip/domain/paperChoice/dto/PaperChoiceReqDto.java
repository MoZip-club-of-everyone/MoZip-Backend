package com.mozip.mozip.domain.paperChoice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaperChoiceReqDto {
    private String choiceId; // bulk update시에만 사용
    private String choice;
}
