package com.mozip.mozip.domain.paperQuestion.service;

import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.service.MozipService;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionResDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionUpdateReqDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaperQuestionManager {

    private final PaperQuestionService paperQuestionService;
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

    public PaperQuestionResDto updatePaperQuestion(String questionId, PaperQuestionUpdateReqDto requestDto) {
        return PaperQuestionResDto.fromEntity(paperQuestionService.updatePaperQuestion(questionId, requestDto));
    }

    public void deletePaperQuestion(String questionId) {
        paperQuestionService.deletePaperQuestion(questionId);
    }
}