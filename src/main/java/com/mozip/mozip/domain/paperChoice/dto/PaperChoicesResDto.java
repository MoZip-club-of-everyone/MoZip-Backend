package com.mozip.mozip.domain.paperChoice.dto;

import com.mozip.mozip.domain.paperChoice.entity.PaperChoice;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PaperChoicesResDto {
    private List<PaperChoiceResDto> choices;
}
