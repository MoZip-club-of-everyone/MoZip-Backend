package com.mozip.mozip.domain.paperQuestion.service;

import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.service.MozipService;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoiceReqDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoiceResDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoicesReqDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoicesResDto;
import com.mozip.mozip.domain.paperChoice.entity.PaperChoice;
import com.mozip.mozip.domain.paperChoice.service.PaperChoiceService;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionResDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionUpdateReqDto;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaperQuestionManager {

    private final PaperQuestionService paperQuestionService;
    private final PaperChoiceService paperChoiceService;
    private final MozipService mozipService;

    public List<PaperQuestionResDto> getPaperQuestionsByMozipId(String mozipId) {
        return paperQuestionService.getPaperQuestionsByMozipId(mozipId)
                .stream().map(PaperQuestionResDto::fromEntity).toList();
    }

    public PaperQuestionResDto getPaperQuestionById(String questionId) {
        return PaperQuestionResDto.fromEntity(paperQuestionService.getPaperQuestionById(questionId));
    }

    public PaperQuestionResDto createPaperQuestion(PaperQuestionCreateReqDto requestDto) {
        Mozip mozip = mozipService.getMozipById(requestDto.getMozipId());
        return PaperQuestionResDto.fromEntity(paperQuestionService.createPaperQuestion(mozip, requestDto));
    }

    @Transactional
    public PaperChoiceResDto createPaperChoice(String questionId, PaperChoiceReqDto dto) {
        PaperQuestion paperQuestion = paperQuestionService.getPaperQuestionById(questionId);
        PaperChoice paperChoice = paperChoiceService.createPaperChoice(paperQuestion, dto.getChoice());
        return PaperChoiceResDto.entityToDto(paperChoice);
    }

    @Transactional
    public PaperChoicesResDto createPaperChoices(String questionId, PaperChoicesReqDto paperChoicesReqDto) {
        List<PaperChoiceReqDto> dtoList = paperChoicesReqDto.getChoices();
        PaperQuestion paperQuestion = paperQuestionService.getPaperQuestionById(questionId);
        List<PaperChoiceResDto> paperChoiceResDtoList = dtoList.stream()
                .map(dto -> paperChoiceService.createPaperChoice(paperQuestion, dto.getChoice()))
                .map(PaperChoiceResDto::entityToDto)
                .toList();
        return new PaperChoicesResDto(paperChoiceResDtoList);
    }

    public PaperChoicesResDto getPaperChoicesByPaperQuestionId(String paperQuestionId) {
        PaperQuestion paperQuestion = paperQuestionService.getPaperQuestionById(paperQuestionId);
        List<PaperChoiceResDto> paperChoiceResDtoList = paperChoiceService.getPaperChoiceByPaperQuestion(paperQuestion)
                .stream()
                .map(PaperChoiceResDto::entityToDto)
                .toList();
        return new PaperChoicesResDto(paperChoiceResDtoList);
    }

    public PaperQuestionResDto updatePaperQuestion(String questionId, PaperQuestionUpdateReqDto requestDto) {
        return PaperQuestionResDto.fromEntity(paperQuestionService.updatePaperQuestion(questionId, requestDto));
    }

    public void deletePaperQuestion(String questionId) {
        paperQuestionService.deletePaperQuestion(questionId);
    }
}