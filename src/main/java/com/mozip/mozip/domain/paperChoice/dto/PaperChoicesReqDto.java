package com.mozip.mozip.domain.paperChoice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaperChoicesReqDto {
    private List<PaperChoiceReqDto> choices;
}
