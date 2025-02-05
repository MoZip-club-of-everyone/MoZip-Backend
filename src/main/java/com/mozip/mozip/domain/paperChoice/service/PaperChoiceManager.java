package com.mozip.mozip.domain.paperChoice.service;

import com.mozip.mozip.domain.paperChoice.dto.PaperChoiceResDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoiceReqDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoicesResDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoicesReqDto;
import com.mozip.mozip.domain.paperChoice.entity.PaperChoice;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaperChoiceManager {
    private final PaperChoiceService paperChoiceService;
    private final PaperQuestionService paperQuestionService;

    public PaperChoiceResDto getPaperChoiceById(String id) {
        PaperChoice paperChoice = paperChoiceService.getPaperChoiceById(id);
        return PaperChoiceResDto.entityToDto(paperChoice);
    }

    public PaperChoiceResDto updatePaperChoice(String id, PaperChoiceReqDto dto) {
        PaperChoice paperChoice = paperChoiceService.updatePaperChoice(id, dto.getChoice());
        return PaperChoiceResDto.entityToDto(paperChoice);
    }

    @Transactional
    public PaperChoicesResDto updatePaperChoices(PaperChoicesReqDto paperChoicesReqDto) {
        List<PaperChoiceReqDto> choicesReqDto = paperChoicesReqDto.getChoices();
        List<PaperChoiceResDto> choicesResDto = choicesReqDto.stream()
                .map(choiceDto -> paperChoiceService.getPaperChoiceById(choiceDto.getChoiceId()))
                .map(choice -> paperChoiceService.updatePaperChoice(choice.getId(), choice.getChoice()))
                .map(PaperChoiceResDto::entityToDto)
                .toList();
        return new PaperChoicesResDto(choicesResDto);
    }

    @Transactional
    public void deletePaperChoice(String id) {
        paperChoiceService.deletePaperChoice(id);
    }
}
