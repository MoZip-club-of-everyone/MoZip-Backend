package com.mozip.mozip.domain.PaperQuestion.service;

import com.mozip.mozip.domain.PaperQuestion.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.PaperQuestion.dto.PaperQuestionUpdateReqDto;
import com.mozip.mozip.domain.PaperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.PaperQuestion.repository.PaperQuestionRepository;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.repository.MozipRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperQuestionService {

    private final PaperQuestionRepository questionRepository;
    private final MozipRepository mozipRepository;

    public List<PaperQuestion> getPaperQuestionsByMozipId(String mozipId) {
        return questionRepository.findByMozipId(mozipId);
    }

    public PaperQuestion getPaperQuestionById(String questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("PaperQuestion이 없습니다 : " + questionId));
    }

    @Transactional
    public PaperQuestion createPaperQuestion(PaperQuestionCreateReqDto requestDto) {
        PaperQuestion paperQuestion = this.dtoToEntity(requestDto);
        return questionRepository.save(paperQuestion);
    }

    @Transactional
    public PaperQuestion updatePaperQuestion(String questionId, PaperQuestionUpdateReqDto requestDto) {
        PaperQuestion question = getPaperQuestionById(questionId);
        question.updateQuestion(requestDto.getQuestion(), requestDto.getDetails(), requestDto.isRequired());
        return questionRepository.save(question);
    }

    @Transactional
    public void deletePaperQuestion(String questionId) {
        PaperQuestion existingQuestion = getPaperQuestionById(questionId);
        questionRepository.delete(existingQuestion);
    }

    public PaperQuestion dtoToEntity(PaperQuestionCreateReqDto requestDto){
        Mozip mozip = mozipRepository.findById(requestDto.getMozipId())
                .orElseThrow(() -> new EntityNotFoundException("Mozip이 없습니다 : " + requestDto.getMozipId()));
        return PaperQuestion.builder()
                .mozip(mozip)
                .question(requestDto.getQuestion())
                .details(requestDto.getDetails())
                .isRequired(requestDto.isRequired())
                .build();
    }
}